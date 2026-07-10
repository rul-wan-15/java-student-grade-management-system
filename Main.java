import GUI.LoginFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main
{
    public static void main(String[] args)
    {
        try
        {
            UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName()
            );
        }
        catch (Exception exception)
        {
            System.out.println("Default appearance will be used.");
        }

        SwingUtilities.invokeLater(() ->
        {
            new LoginFrame().setVisible(true);
        });
    }
}
