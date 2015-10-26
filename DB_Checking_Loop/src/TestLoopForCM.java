public class TestLoopForCM {
	public static void main(String args[]) {
		if (false) {
			// only run once
			SendSMSLoopForCM cm = new SendSMSLoopForCM();
			cm.beginToCheck();
		} else {
			// keep running
			SendSMSLoopForCM cm = new SendSMSLoopForCM();
			Thread t = new Thread(cm);
			t.start();
		}
	}
}
