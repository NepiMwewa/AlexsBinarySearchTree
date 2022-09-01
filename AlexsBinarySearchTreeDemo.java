import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

   public class AlexsBinarySearchTreeDemo extends JFrame
   implements ActionListener
   {
      private AlexsBinarySearchTree avlTree = new AlexsBinarySearchTree();   
      private JLabel inputResultLabel;
      private JTextField inputResultTextField;
    
      private JLabel inputLabel;
      private JTextField inputTextField;
      
      private JTextArea arrayTextArea;
   
      public AlexsBinarySearchTreeDemo()
      {
          setTitle("Alexs Binary Search Tree");
          
          //make and set inputResult and tree to north
          JPanel resultPanel = new JPanel(new GridLayout(1,2));
          inputResultLabel = new JLabel("Input Result: ");
          inputResultTextField = new JTextField();
          resultPanel.add(inputResultLabel);
          resultPanel.add(inputResultTextField);
          inputResultTextField.setEditable(false);     
          add(resultPanel, BorderLayout.NORTH);
          
          // Leave center for binary tree view
          
          //make and set input label and input textfield in center
          inputLabel = new JLabel("Enter the number of nodes for the tree: ");
          inputTextField = new JTextField();
          JPanel inputPanel = new JPanel(new GridLayout(1,2));
          inputPanel.add(inputLabel);
          inputPanel.add(inputTextField);
          inputTextField.addActionListener(this);
          add(inputPanel, BorderLayout.CENTER);
          
          //make and set output TextArea to south
          JPanel outputPanel = new JPanel(new GridLayout(1,1));
          arrayTextArea = new JTextArea();
          arrayTextArea.setEditable(false);
          outputPanel.add(arrayTextArea);
          add(outputPanel, BorderLayout.SOUTH);
          
          // Set up the frame
          pack();
          setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
          setVisible(true);
      }
      
      JPanel view = null;
      // call when enter key is pressed
      public void actionPerformed(ActionEvent evt)
      {
          String cmdStr = inputTextField.getText();
          Scanner sc = new Scanner(cmdStr);
          int value = sc.nextInt();
          //if input is equal to 40 or more than 40 do the following
          if (value >= 40)
          {
               //call arryGen
              avlTree.arrayGen(value);
              if (view != null)
                  remove(view);
              view = avlTree.getView();
              add(view);// add to view
              pack();// pack
              validate();// validate
              inputResultTextField.setText(" ");// set text to blank
              //call textArea to get unsorted and sorted array
              arrayTextArea.setText(avlTree.textArea());
          }
          else// if smaller than 40 set input label text field to
          {
            inputLabel.setText("please enter 40 or more");
          }
      } 
	 
      public static void main(String [ ] args)
      {
         AlexsBinarySearchTreeDemo atd =  new AlexsBinarySearchTreeDemo();
      }    
   }