# My ambitious attempt at understanding Spring

## Example
```java
@Controller
public class UserProfileComponent extends JPanel {
    @UIComponent(text = "Edit")
    private JButton editButton;

    @OnClick(component = "editButton")
    private void onClick() {
        System.out.println("Edit!");
    }

    @PostConstruct
    private void setupUI() {
        add(editButton);
    }

    public UserProfileComponent() {
        setLayout(new FlowLayout());
    }
}

@WinterApplication
public class AppTest {
    @UIComponent(text = "Main Frame")
    private JFrame mainFrame;

    @UIComponent(text = "Click Me!")
    private JButton clicker;

    @OnClick(component = "clicker")
    private void onClick() {
        System.out.println("Clicked!!!");
    }

    @UIContainer(children = { "clicker" })
    private UserProfileComponent profileContainer;

    @EntryPoint
    public void init() {
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(300, 200);
        mainFrame.setLayout(new FlowLayout());

        mainFrame.add(profileContainer);
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        WinterApplicationRunner.run(AppTest.class);
    }
}
```