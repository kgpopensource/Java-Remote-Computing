//package javaremotecomputing;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.*;
import java.awt.event.*;

public class screenconfig extends JPanel
{
  JLabel jl1,jl2,jl3;

  JComboBox jcb1,jcb2,jcb3;

  String[] screentype={"Full Colors","16 bit color","256 colors","black and white"};
  String[] resolution={"1920-1080","1366-768","1280-1024","1280-720","1024-768","800-600"};
  String[] compression={"0.1","0.2","0.3","0.4","0.5","0.6","0.7","0.8","0.9","1.0"};
 
  int type,size,comp;
  
  float compress[];

  Dimension[] d;

  screenconfig()
  {
     d=new Dimension[6];
     d[0]=new Dimension(1920,1080);
     d[1]=new Dimension(1366,768);
     d[2]=new Dimension(1280,1024);
     d[3]=new Dimension(1280,720);
     d[4]=new Dimension(1024,768);
     d[5]=new Dimension(800,600);

	 compress=new float[10];
	 compress[0]=0.1f;
	 compress[1]=0.2f;
	 compress[2]=0.3f;
	 compress[3]=0.4f;
	 compress[4]=0.5f;
	 compress[5]=0.6f;
	 compress[6]=0.7f;
	 compress[7]=0.8f;
	 compress[8]=0.9f;
	 compress[9]=1f;
	 
	 
     this.setLayout(new GridLayout(3,2));
     jl1=new JLabel("Type of screen :");
     this.add(jl1);

     jcb1=new JComboBox(screentype);
     jcb1.addActionListener(new ActionListener()
     {
        public void actionPerformed(ActionEvent e)
        {
            type=jcb1.getSelectedIndex();
        }
     });
     this.add(jcb1);

     jl2=new JLabel("Resolotion :");
     this.add(jl2);

     jcb2=new JComboBox(resolution);
     jcb2.addActionListener(new ActionListener()
     {
        public void actionPerformed(ActionEvent e)
        {
            size=jcb2.getSelectedIndex();
        }
     });
     this.add(jcb2);
	 
	 jl3=new JLabel("Compression :");
	 this.add(jl3);
	 jcb3=new JComboBox(compression);
     jcb3.addActionListener(new ActionListener()
     {
        public void actionPerformed(ActionEvent e)
        {
            comp=jcb3.getSelectedIndex();
        }
     });
	 this.add(jcb3);
	 
     
  }
}
