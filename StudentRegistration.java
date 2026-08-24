package JDBC;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
public class StudentRegistration extends JFrame implements ActionListener{
   JLabel Name, rollno , gender, branch;
   JTextField txtname , txtroll, txtbranch;
   JRadioButton male,female;
   ButtonGroup genderGroup;
   JCheckBox terms;
   JButton submit , reset;
   public StudentRegistration(){
      setTitle("Student Registration Form");
      setSize(450,450);
      setLayout(null);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      // Student name
      Name= new JLabel("Student Name:");
      Name.setBounds(30,30,120,30);
      add(Name);
      txtname=new JTextField();
      txtname.setBounds(170,30,180,30);
      add(txtname);
      //Roll number
      rollno=new JLabel("Roll Number:");
      rollno.setBounds(30,80,120,30);
      add(rollno);
      txtroll=new JTextField();
      txtroll.setBounds(170,80,180,30);
      add(txtroll);
      //Branch
      branch=new JLabel("Branch:");
      branch.setBounds(30,130,120,30);
      add(branch);
      txtbranch=new JTextField();
      txtbranch.setBounds(170,130,180,30);
      add(txtbranch);
      //gender
      gender = new JLabel("Gender:");
      gender.setBounds(30, 180, 120, 30);        
      add(gender);
      //male
      male=new JRadioButton("Male");
      male.setBounds(170,180,80,30);
      add(male);
      //female
      female=new JRadioButton("Female");
      female.setBounds(260,180,100,30);
      add(female);
      //group
      genderGroup=new ButtonGroup();
      genderGroup.add(male);
      genderGroup.add(female);
      // terms and condition
      terms=new JCheckBox("I accept Terms & Condition");
      terms.setBounds(30,230,250,30);
      add(terms);
      //submit button
      submit= new JButton("Submit");
      submit.setBounds(80,290,100,35);
      submit.addActionListener(this);
      add(submit);
      //reset button
      reset= new JButton("Reset");
      reset.setBounds(220,290,100,35);
      reset.addActionListener(this);
      add(reset);
      setVisible(true);
      
   }
   
   @Override
   public void actionPerformed(ActionEvent e){
      // reset
      if(e.getSource()==reset){
         txtname.setText("");
         txtroll.setText("");
         txtbranch.setText("");
         genderGroup.clearSelection();
         terms.setSelected(false);
         return;
      }
      //submit button
       if (e.getSource() == submit) {
      if(txtname.getText().trim().isEmpty()){
         JOptionPane.showMessageDialog(this,"Please enter Student Name");
         return;
      }
      if(txtroll.getText().trim().isEmpty()){
         JOptionPane.showMessageDialog(this,"Please enter Student Roll number");
         return;
      }
      if(!male.isSelected() && !female.isSelected()){
         JOptionPane.showMessageDialog(this,"Please Select Gender");
         return;
      }
      if(txtbranch.getText().trim().isEmpty()){
         JOptionPane.showMessageDialog(this,"Please enter Student Branch");
         return;
      }
      if(!terms.isSelected()){
         JOptionPane.showMessageDialog(this,"Please accept Terms & condition");
         return;
      }
      // Get values from form
      String name = txtname.getText().trim();
            String branchName = txtbranch.getText().trim();

            int rollNumber;
      
            // Convert roll number into integer
            try {
                rollNumber = Integer.parseInt(txtroll.getText().trim());
            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                    this,
                    "Roll Number must be a number"
                );

                return;
            }

            // Get selected gender
            String selectedGender;

            if (male.isSelected()) {
                selectedGender = "Male";
            } else {
                selectedGender = "Female";
            }

            // JDBC
            try {

                // Load MySQL Driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Create Connection
                Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/student_registration",
                    "root",
                    "Admin_21"
                );

                // SQL Query
                String sql =
                    "INSERT INTO students " +
                    "(roll_no, name, branch, gender, terms_accepted) " +
                    "VALUES (?, ?, ?, ?, ?)";

               // PreparedStatement
                PreparedStatement ps = con.prepareStatement(sql);

                // Set values
                ps.setInt(1, rollNumber);
                ps.setString(2, name);
                ps.setString(3, branchName);
                ps.setString(4, selectedGender);
                ps.setBoolean(5, terms.isSelected());

                // Execute INSERT
                ps.executeUpdate();

                // Success message
                JOptionPane.showMessageDialog(
                    this,
                    "Student Registered Successfully!"
                );
                // Close resources
                ps.close();
                con.close();

                // Clear form after successful registration
                txtname.setText("");
                txtroll.setText("");
                txtbranch.setText("");
                genderGroup.clearSelection();
                terms.setSelected(false);

            } catch (SQLIntegrityConstraintViolationException ex) {

                JOptionPane.showMessageDialog(
                    this,
                    "This Roll Number already exists!"
                );

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                    this,
                    "Database Error: " + ex.getMessage()
                );
            }
         }
   }
   

    public static void main(String[] args) {        
      new StudentRegistration();    }

}