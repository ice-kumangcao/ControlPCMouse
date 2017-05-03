import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ReviceMouseMessage implements Runnable{

	public static void main(String args[]){
		new Thread(new ReviceMouseMessage()).start();
	}
	private Robot robot;
	private int OPERATE_PORT=9999;
	private ServerSocket server;
	private Socket socket;
	public ReviceMouseMessage(){
		try{
			server=new ServerSocket(OPERATE_PORT);
			robot=new Robot();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try{
				System.out.println("等待接受信息******");
				socket=server.accept();
				socket.setSoTimeout(100000);
				DataInputStream dataIn = new DataInputStream(socket.getInputStream());        
                String info="";
                int r;
                while((r=dataIn.read()) != -1){
                    info +=""+(char)r;   //把字节数组中所有元素都变为字符型
                    //  System.out.println("当前读到的数据时"+info);
                }
                dataIn.close();
                System.out.println("数据流断开"+info);
                if(info!=null){
                    String s[] = info.trim().split(","); 
                    switch(s[0].trim()){
                    	case "Movemouse":
                    		if (s.length == 3) {
                                float x = Float.parseFloat(s[1].trim());
                                float y = Float.parseFloat(s[2].trim());
                                System.out.println("输出鼠标的信息"+x+"  "+ y);
                                robot.mouseMove((int)x, (int)y);
                            }
                    		break;
                    	case "MouseClick":
                    		robot.mousePress(InputEvent.BUTTON1_MASK);
                    		robot.mouseRelease(InputEvent.BUTTON1_MASK);
                    		break;
                    	case "MouseRight":
                    		robot.mousePress(InputEvent.BUTTON3_MASK);
                            robot.mouseRelease(InputEvent.BUTTON3_MASK);
                            break;
                    }
                }
			}catch(IOException e){
				System.out.println("已经停止连接");
			}
		}
	}

}
