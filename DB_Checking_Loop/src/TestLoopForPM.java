public class TestLoopForPM {
	public static void main(String args[]) {

		if (false) {
			// only run once
			SendSMSLoopForPM pm = new SendSMSLoopForPM();
			pm.beginToCheck();
		} else {
			// keep running
			SendSMSLoopForPM pm = new SendSMSLoopForPM();
			Thread t = new Thread(pm);
			t.start();
		}

	}
}
