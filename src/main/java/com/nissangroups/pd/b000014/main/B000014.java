

public class B000014 {
	
	/** Constant LOG. */
	private static final Log LOG = LogFactory.getLog(B000014.class);
	
	/** Variable por cd. */
	private static String porCd = null;
	
	/** Variable production order stage code */
	private static String prodOrderStageCode = null;
	
	private B000014() {
	}

	/**
	 * Batch B000014 Execution Start from this Main Method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		
		//verifying arguments
		if(args==null || args.length!=2){
			LOG.error(PDMessageConsants.M00107.replace(
					PDConstants.ERROR_MESSAGE_1,
					PDConstants.POR_CD));
			LOG.error("Arguments [2] expected, [POR_CD,PROD_ORDER_STAGE_CD]");
            return;
		}
		porCd = args[0];
		prodOrderStageCode = args[1];
		run();
	}
	
	/**
	 * Run.
	 */
	@SuppressWarnings("resource")
	private static void run(){
		String[] batchConfig = { B000014Constants.CONFIG_PATH };
		ApplicationContext context = new ClassPathXmlApplicationContext(
				batchConfig);
		JobLauncher jobLauncher = (JobLauncher) context.getBean(PDConstants.jobLauncher);
		Job job = (Job) context.getBean(B000014Constants.B000014);

		try {

			JobParameters parameter = new JobParametersBuilder()
					.addString(PDConstants.PRMTR_POR, porCd)
					.addString(PDConstants.PRMTR_PRODUCTION_STAGE_CODE, prodOrderStageCode)
					.toJobParameters();
			JobExecution execution = jobLauncher.run(job, parameter);
			String exitStatus =PDConstants.EXIT_STATUS;
			exitStatus += execution.getStatus().toString();
			LOG.info(exitStatus);

		} catch (Exception e) {
			LOG.error("Exception in thread" + e);

		}

	}
}
