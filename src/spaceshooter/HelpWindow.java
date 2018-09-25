package spaceshooter;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class HelpWindow extends JFrame implements ActionListener{
	
	JTextArea text = new JTextArea(20 , 30);
	JButton ok = new JButton("OK!");
	JPanel panel1 = new JPanel(new FlowLayout());
	JPanel panel2 = new JPanel(new FlowLayout());
	JPanel panel3 = new JPanel(new FlowLayout());
	ImageIcon imageIcon = new ImageIcon("help.png");
	JLabel label = new JLabel();
	
	public HelpWindow(){
		super("Help!");
		setResizable(false);
		setSize(450,350);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		text.setText("Comandos:\n\tW : mover para cima.\n\tS : mover para baixo.\n\tA : mover para esquerda.\n\tD : mover para direita.\n\tBotão esquerdo para atirar.\n\n\tBotão direito para habilitar os mísseis.\n\n\tEspaço: ativar o modo turbo.\n\n\tBoa Sorte!");
		text.setEditable(false);
		label.setIcon(imageIcon);
		ok.addActionListener(this);
		panel2.add(label);
		text.setBackground(getBackground());
		panel1.add(text);
		panel3.add(ok);
		add(panel2, BorderLayout.NORTH);
		add(panel1);
		add(panel3, BorderLayout.SOUTH);
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		dispose();
		
	}

}
