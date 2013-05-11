import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.SwingUtilities;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class Main extends javax.swing.JFrame {
  private JLabel jLabel1;
	private JLabel jLabel2;
	private JTextField gtexNum2;
	private JButton gbutCalcular;
	private JTextArea gtexaResult;
	private JLabel glabMensaje;
	private JScrollPane jScrollPane1;
	private JTextField gtexNum1;
	private BigInteger gbiNum1;
	private BigInteger gbiNum2;

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Main inst = new Main();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
				try {
					UIManager.setLookAndFeel(
					        UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	public Main() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			setTitle("Algoritmo de Karatsuba");
			getContentPane().setLayout(null);
			{
				jLabel1 = new JLabel();
				getContentPane().add(jLabel1);
				jLabel1.setText("numero 1");
				jLabel1.setBounds(26, 23, 61, 16);
			}
			{
				jLabel2 = new JLabel();
				getContentPane().add(jLabel2);
				jLabel2.setText("numero 2");
				jLabel2.setBounds(26, 59, 61, 16);
			}
			{
				gtexNum1 = new JTextField();
				getContentPane().add(gtexNum1);
				gtexNum1.setBounds(105, 20, 259, 23);
			}
			{
				gtexNum2 = new JTextField();
				getContentPane().add(gtexNum2);
				gtexNum2.setBounds(105, 55, 259, 23);
			}
			{
				gbutCalcular = new JButton();
				getContentPane().add(gbutCalcular);
				gbutCalcular.setText("Calcular");
				gbutCalcular.setBounds(12, 152, 84, 23);
				gbutCalcular.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						gbutCalcularActionPerformed(evt);
					}
				});
			}
			{
				jScrollPane1 = new JScrollPane();
				getContentPane().add(jScrollPane1);
				jScrollPane1.setBounds(117, 90, 247, 144);
				{
					gtexaResult = new JTextArea();
					jScrollPane1.setViewportView(gtexaResult);
					gtexaResult.setPreferredSize(new java.awt.Dimension(244, 141));
					gtexaResult.setEditable(false);
				}
			}
			{
				glabMensaje = new JLabel();
				getContentPane().add(glabMensaje);
				glabMensaje.setBounds(0, 242, 384, 20);
				glabMensaje.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			}
			pack();
			setSize(400, 300);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
	
	private void gbutCalcularActionPerformed(ActionEvent evt) {
		// raliza el calculo
		try{
			gbiNum1 = new BigInteger(gtexNum1.getText());
			gbiNum2 = new BigInteger(gtexNum2.getText());
			
			BigInteger cero = new BigInteger("0");
			if( gbiNum1.compareTo(cero) == -1 || gbiNum2.compareTo(cero) == -1 ){
			//	mostrarError("NUMEROS NEGATIVOS NO PERMITIDOS");
				gtexNum1.requestFocus();
				//return;
			}
		}catch(Exception ex){
			mostrarError("LOS VALORES INGRESADOS NO SON NUMERICOS");
			gtexNum1.requestFocus();
			return;
		}
		mostrarMensaje("");
		gtexaResult.setText(karatsuba(gbiNum1, gbiNum2)+"");
	}

	private void mostrarMensaje(String mensaje) {
		// // MUESTRA UN TEXTO EN negro EN LA BARRA DE MENSAJE
		glabMensaje.setForeground(Color.BLACK);
		glabMensaje.setText(mensaje);
	}

	private void mostrarError(String string) {
		// MUESTRA UN TEXTO EN ROJO EN LA BARRA DE MENSAJE
		glabMensaje.setForeground(Color.RED);
		glabMensaje.setText(string);
		
	}
	
	public static BigInteger karatsuba(BigInteger x, BigInteger y) {

        // hallar el numero mas grande
        int N = Math.max(x.bitLength(), y.bitLength());
        // cuando son numeros con logitud menor a 1000 es mas efectiva la multiplicacion normal
        if (N <= 1000) return x.multiply(y);

        // hallar la longitud media del numero grande
        N = (N / 2) + (N % 2);

        // x = a + 2^N b,   y = c + 2^N d
        BigInteger b = x.shiftRight(N);
        BigInteger a = x.subtract(b.shiftLeft(N));
        BigInteger d = y.shiftRight(N);
        BigInteger c = y.subtract(d.shiftLeft(N));

        // realizar calculos parciales
        BigInteger ac    = karatsuba(a, c);
        BigInteger bd    = karatsuba(b, d);
        BigInteger abcd  = karatsuba(a.add(b), c.add(d));

        return ac.add(abcd.subtract(ac).subtract(bd).shiftLeft(N)).add(bd.shiftLeft(2*N));
    }

}
