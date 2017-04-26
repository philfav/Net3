
public class BetterBuffer {
	
	InfiniteBuffer buf;
	int size;
	int remSpace;
	
	public BetterBuffer(){
		buf = new InfiniteBuffer(1000);
		size = remSpace = buf.getBufferSize();
	}
	
	public int getBufferSize() {
		return buf.getBufferSize();
	}

	public final int getBase() {
		return buf.getBase();
	}

	public final int getNext() {
		return buf.getNext();
	}

	public synchronized void append(byte[] data, int dataOffset, int len) {
		if (remSpace - len < 0){
			System.out.println("Ovewriting data! Aborting append");
			return;
		}
		
		remSpace -= len;
		buf.append(data, dataOffset, len);
	}

	public synchronized void read(byte[] data, int len) {
		if (remSpace + len > size){
			System.out.println("Reading data that has not yet been written! Aborting read");
		}
		
		if (len == 0)
			return;
		
		remSpace += len;
		
		buf.copyOut(data, buf.getBase(), len);
		buf.advance(len);
	}
	
	public synchronized int getFreeSpace(){
		return remSpace;
	}
	
	public synchronized int getUsedSpace(){
		return size - remSpace;
	}
}
