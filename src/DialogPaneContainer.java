import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Tim on 03/03/2017.
 */

import net.miginfocom.swing.MigLayout;

public class DialogPaneContainer extends JDialog implements ActionListener{

    //made variables private
    private JTextField idField, ppsField, surnameField, firstNameField, salaryField;
    private JComboBox<String> genderCombo, departmentCombo, fullTimeCombo;
    private JButton save, cancel;
//    private EmployeeDetails parent;

    JPanel dialogPane(EmployeeDetails parent) {
        JPanel empDetails, buttonPanel;
        empDetails = new JPanel(new MigLayout());
        buttonPanel = new JPanel();
        JTextField field;

        String labelConstant = "growx, pushx";
        String textFieldConstant = "growx, pushx, wrap";

        empDetails.setBorder(BorderFactory.createTitledBorder("Employee Details"));

        empDetails.add(new JLabel("ID:"), labelConstant);
        empDetails.add(idField = new JTextField(20), textFieldConstant);
        idField.setEditable(false);

        empDetails.add(new JLabel("PPS Number:"), labelConstant);
        empDetails.add(ppsField = new JTextField(20), textFieldConstant);

        empDetails.add(new JLabel("Surname:"), labelConstant);
        empDetails.add(surnameField = new JTextField(20), textFieldConstant);

        empDetails.add(new JLabel("First Name:"), labelConstant);
        empDetails.add(firstNameField = new JTextField(20), textFieldConstant);

        empDetails.add(new JLabel("Gender:"), labelConstant);
        empDetails.add(genderCombo = new JComboBox<>(parent.gender), textFieldConstant);

        empDetails.add(new JLabel("Department:"), "growx, pushx");
        empDetails.add(departmentCombo = new JComboBox<>(parent.department), textFieldConstant);

        empDetails.add(new JLabel("Salary:"), labelConstant);
        empDetails.add(salaryField = new JTextField(20), textFieldConstant);

        empDetails.add(new JLabel("Full Time:"), labelConstant);
        empDetails.add(fullTimeCombo = new JComboBox<>(parent.fullTime), textFieldConstant);

        buttonPanel.add(save = new JButton("Save"));
        save.addActionListener(this);
        save.requestFocus();
        buttonPanel.add(cancel = new JButton("Cancel"));
        cancel.addActionListener(this);
//
//        empDetails.add(buttonPanel, "span 2,growx, pushx,wrap");
//        // loop through all panel components and add fonts and listeners
//        for (int i = 0; i < empDetails.getComponentCount(); i++) {
//            empDetails.getComponent(i).setFont(this.parent.font1);
//            if (empDetails.getComponent(i) instanceof JComboBox) {
//                empDetails.getComponent(i).setBackground(Color.WHITE);
//            }// end if
//            else if(empDetails.getComponent(i) instanceof JTextField){
//                field = (JTextField) empDetails.getComponent(i);
//                if(field == ppsField)
//                    field.setDocument(new JTextFieldLimit(9));
//                else
//                    field.setDocument(new JTextFieldLimit(20));
//            }
//        }
//        idField.setText(Integer.toString(this.parent.getNextFreeId()));
        return empDetails;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
