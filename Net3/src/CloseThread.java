public class CloseThread extends Thread {
	private StudentSocketImpl sock;
	
	public CloseThread(StudentSocketImpl sock){
		this.sock = sock;
	}
	
	/**
	 * This function will run this thread until the calling StudentSocketImpl fully closes.
	 * In running server2/client2, that will also signal the end of execution for the java application.
	 */
	@Override
	public void run(){
		while (sock.getState() != StudentSocketImpl.State.CLOSED){ //Wait until connection is fully closed.
			synchronized(sock){ //Synchronized with the monitor from the calling socketImpl
				try {
					sock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		sock.notifyAll();
	}
}
