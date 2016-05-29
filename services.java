//package javaremotecomputing;

import java.io.FileNotFoundException;
import java.io.*;
import java.rmi.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.util.Iterator;


public class services implements servicesinterface
{
    Robot rbt;

    
    int screentype=0;
    Toolkit tk;
    Dimension server_dimension,client_dimension;
    ImageIcon ii;
    Double width_scale,height_scale;

    String filename;
    FileOutputStream fos;
	
	byte[] bufff;

	BufferedImage bi;
	BufferedImage jp;
         
    Boolean ok;

    int counter;
	
	float compression=0.5f;
	static int a=0;

    services()
    {
        tk=Toolkit.getDefaultToolkit();
        server_dimension=tk.getScreenSize();
        try
        {
            rbt = new Robot();
        }
        catch (AWTException ex)
        {
            Logger.getLogger(services.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
	
	public void setCompression(float f) throws RemoteException
	{
	    compression=f;
	}

    public byte[] getscreen() throws RemoteException, AWTException, IOException, NullPointerException
    {
		//if(a==0)
		{
			a=1;
	     if(screentype==0)
         {
            bi=new BufferedImage(server_dimension.width, server_dimension.height, BufferedImage.TYPE_INT_ARGB); 
			jp=new BufferedImage(client_dimension.width, client_dimension.height, BufferedImage.TYPE_INT_ARGB);
         }
         else if(screentype==1)
         {
            bi=new BufferedImage(server_dimension.width, server_dimension.height, BufferedImage.TYPE_USHORT_555_RGB); 
			jp=new BufferedImage(client_dimension.width, client_dimension.height, BufferedImage.TYPE_USHORT_555_RGB);
         }
         else if(screentype==2)
         {
            bi=new BufferedImage(server_dimension.width, server_dimension.height, BufferedImage.TYPE_BYTE_INDEXED); 
			jp=new BufferedImage(client_dimension.width, client_dimension.height, BufferedImage.TYPE_BYTE_INDEXED);
         }
         else if(screentype==3)
         {
            bi=new BufferedImage(server_dimension.width, server_dimension.height, BufferedImage.TYPE_BYTE_GRAY); 
			jp=new BufferedImage(client_dimension.width, client_dimension.height, BufferedImage.TYPE_BYTE_GRAY);
         }
		 ByteArrayOutputStream out=new ByteArrayOutputStream();
			bi=rbt.createScreenCapture(new Rectangle(server_dimension));
			Image i=bi.getScaledInstance(client_dimension.width, client_dimension.height,BufferedImage.SCALE_SMOOTH);
			Graphics g=jp.createGraphics();
			g.drawImage(i, 0,0, null);
			Iterator writers = ImageIO.getImageWritersBySuffix("jpeg");    
            if (!writers.hasNext()) 
            {
               throw new IllegalStateException("No writers found");
            }
			ImageWriter writer = (ImageWriter) writers.next();
            ImageOutputStream ios = ImageIO.createImageOutputStream(out);
            writer.setOutput(ios);
            ImageWriteParam param = writer.getDefaultWriteParam();  
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(compression);
			writer.write(null, new IIOImage(jp, null, null), param);    
            ios.close();
            writer.dispose();
			bi=null;
			jp=null;	
			out.close();
			return out.toByteArray();
		}
    }

    public Dimension getscreensize() throws RemoteException
    {
         return(server_dimension);
    }
    public void setscreentype(int a) throws RemoteException
    {
         if(a==0)
         {
             screentype=0;
         }
         else if(a==1)
         {
             screentype=1;
         }
         else if(a==2)
         {
             screentype=2;
         }
         else if(a==3)
         {
             screentype=3;
         }
    }

    public void setscaling(Dimension dd) throws RemoteException
    {
         client_dimension=dd;
    }

    public void setscreenscale(Dimension dd) throws RemoteException
    {
         width_scale=(double)((double)server_dimension.width/(double)dd.width);
         height_scale=(double)((double)server_dimension.height/(double)dd.height);
    }

    public void move(int a,int b,int click,int type) throws RemoteException
    {
        int x,y;
        x=(int)(a*width_scale);
        y=(int)(b*height_scale);
        if(type==0)
        {
            if(click==0)
            {
                rbt.mouseMove(x,y);
                rbt.mousePress(InputEvent.BUTTON1_MASK);
            }
            else if(click==1)
            {
                rbt.mouseMove(x,y);
                rbt.mousePress(InputEvent.BUTTON3_MASK);
            }
        }
        else if(type==1)
        {
            if(click==0)
            {
                rbt.mouseMove(x,y);
                rbt.mouseRelease(InputEvent.BUTTON1_MASK);
            }
            else if(click==1)
            {
                rbt.mouseMove(x,y);
                rbt.mouseRelease(InputEvent.BUTTON3_MASK);
            }
        }
        else if(type==2)
        {
            rbt.mouseMove(x,y);
        }
        else if(type==3)
        {
            rbt.mouseMove(x, y);
        }
    }

    public void keypress(final int k) throws RemoteException
    {
        Thread t=new Thread()
        {
           public void run()
           {
              try 
              {
                 Thread.sleep(500);
                 rbt=new Robot();
                 rbt.keyPress(k);
              }
              catch (InterruptedException ex) 
              {
                 Logger.getLogger(services.class.getName()).log(Level.SEVERE, null, ex);
              }
              catch(AWTException e)
              {
              }
           }
        };
        t.start();
    }
    public void keyrelease(final int k) throws RemoteException
    {
        Thread t=new Thread()
        {
           public void run()
           {
              try
              {
                 Thread.sleep(500);
                 rbt=new Robot();
                 rbt.keyRelease(k);
              }
              catch (InterruptedException ex)
              {
                 Logger.getLogger(services.class.getName()).log(Level.SEVERE, null, ex);
              }
              catch(AWTException e)
              {
              }
           }
        };
        t.start();
    }

    public void sendname(String name) throws RemoteException
    {
        this.filename=name;
        try
        {
            fos = new FileOutputStream(new File(filename));
        }
        catch (FileNotFoundException ex) {
            System.out.println("file not found servie");
        }
    }

    public void sendfile(byte[] b) throws RemoteException
    {
        try
        {
            fos.write(b);
            counter++;
            if(counter==25)
            {
               counter=0;
               try
               {
                  fos.close();
               }
               catch (IOException ex)
               {
                  server_gui.jt1.append("\n-->could not write file");
               }
            }
        }
        catch (FileNotFoundException ex)
        {
            server_gui.jt1.append("\n-->could not write file");
        }
        catch (IOException ex)
        {
            server_gui.jt1.append("\n-->could not write file");
        }
    }

    public void sendchat(String s) throws RemoteException
    {
        if(server_gui.chatrun==1)
        {
           chat.jt1.append(s);
        }
    }

    public int chatrunning() throws RemoteException
    {
        if(server_gui.chatrun==1)
        {
            return(1);
        }
        else
        {
            return(0);
        }

    }

    public String getusername() throws RemoteException
    {
        return(serverconfig.username);
    }

    public String getpassword() throws RemoteException
    {
        return(serverconfig.password);
    }

    public void check() throws RemoteException
    {
    }
}
