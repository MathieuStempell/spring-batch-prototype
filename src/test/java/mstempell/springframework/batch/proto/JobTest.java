package mstempell.springframework.batch.proto;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.support.ListItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mstempell.springframework.batch.proto.bean.Game;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/batch-context.xml")
public class JobTest {

	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job simpleJob;

	@Autowired
	private ListItemWriter<Game> listItemWriter;

	private StepExecution runOKAndGetStepExecution(Job job, String StepName) throws Exception {
		
		JobExecution execution = jobLauncher.run(job,
				new JobParametersBuilder().addString("inputFile", "data.csv").toJobParameters());
		assertEquals(BatchStatus.COMPLETED, execution.getStatus());
		
		StepExecution stepExecution = null;
		for (StepExecution currentStepExecution : execution.getStepExecutions()) {
			logger.info("Processed step: " + currentStepExecution);
			if (currentStepExecution.getStepName().equals(StepName)) {
				stepExecution = currentStepExecution;
			}
		}
		return stepExecution;
	}

	@Test
	public void testSimpleJob() throws Exception {
		
		assertEquals(5, runOKAndGetStepExecution(simpleJob, "simpleStep").getReadCount());
		
		@SuppressWarnings("serial")
		Map<Integer, String> gamesNames = new HashMap<Integer, String>() {
			{
				put(1, "Bobo");
				put(2, "Cauldron");
				put(3, "APB");
				put(4, "Cybernoid");
				put(5, "Kult");
			}
		};
		for (Game game : listItemWriter.getWrittenItems()) {
			logger.info(game);
			assertEquals(gamesNames.get(game.getId()), game.getGameName());
		}
	}
}
