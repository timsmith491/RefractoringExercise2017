/*
 * 
 * This is a dialog for adding new Employees and saving records to file
 * 
 * */

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class AddRecordDialog extends JDialog implements ActionListener {

	//made variables private
	private JTextField idField, ppsField, surnameField, firstNameField, salaryField;
	private JComboBox<String> genderCombo, departmentCombo, fullTimeCombo;
	private JButton save, cancel;
	private EmployeeDetails parent;

	// constructor for add record dialog
	public AddRecordDialog(EmployeeDetails parent) {
		setTitle("Add Record");
		setModal(true);
		this.parent = parent;
		this.parent.setEnabled(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JScrollPane scrollPane = new JScrollPane(dialogPane());
		setContentPane(scrollPane);
		
		getRootPane().setDefaultButton(save);
		
		setSize(500, 370);
		setLocation(350, 250);
		setVisible(true);
	}

	// initialize dialog container
	public Container dialogPane() {
		JPanel empDetails, buttonPanel;
		empDetails = new JPanel(new MigLayout());
		buttonPanel = new JPanel();
		JTextField field;

		empDetails.setBorder(BorderFactory.createTitledBorder("Employee Details"));

		empDetails.add(new JLabel("ID:"), "growx, pushx");
		empDetails.add(idField = new JTextField(20), "growx, pushx, wrap");
		idField.setEditable(false);
		

		empDetails.add(new JLabel("PPS Number:"), "growx, pushx");
		empDetails.add(ppsField = new JTextField(20), "growx, pushx, wrap");

		empDetails.add(new JLabel("Surname:"), "growx, pushx");
		empDetails.add(surnameField = new JTextField(20), "growx, pushx, wrap");

		empDetails.add(new JLabel("First Name:"), "growx, pushx");
		empDetails.add(firstNameField = new JTextField(20), "growx, pushx, wrap");

		empDetails.add(new JLabel("Gender:"), "growx, pushx");
		empDetails.add(genderCombo = new JComboBox<String>(this.parent.gender), "growx, pushx, wrap");

		empDetails.add(new JLabel("Department:"), "growx, pushx");
		empDetails.add(departmentCombo = new JComboBox<String>(this.parent.department), "growx, pushx, wrap");

		empDetails.add(new JLabel("Salary:"), "growx, pushx");
		empDetails.add(salaryField = new JTextField(20), "growx, pushx, wrap");

		empDetails.add(new JLabel("Full Time:"), "growx, pushx");
		empDetails.add(fullTimeCombo = new JComboBox<String>(this.parent.fullTime), "growx, pushx, wrap");

		buttonPanel.add(save = new JButton("Save"));
		save.addActionListener(this);
		save.requestFocus();
		buttonPanel.add(cancel = new JButton("Cancel"));
		cancel.addActionListener(this);

		empDetails.add(buttonPanel, "span 2,growx, pushx,wrap");
		// loop through all panel components and add fonts and listeners
		for (int i = 0; i < empDetails.getComponentCount(); i++) {
			empDetails.getComponent(i).setFont(this.parent.font1);
			if (empDetails.getComponent(i) instanceof JComboBox) {
				empDetails.getComponent(i).setBackground(Color.WHITE);
			}// end if
			else if(empDetails.getComponent(i) instanceof JTextField){
				field = (JTextField) empDetails.getComponent(i);
				if(field == ppsField)
					field.setDocument(new JTextFieldLimit(9));
				else
				field.setDocument(new JTextFieldLimit(20));
			}
		}
		idField.setText(Integer.toString(this.parent.getNextFreeId()));
		return empDetails;
	}


	public void addRecord() {
		boolean fullTime = false;
		Employee theEmployee;//create a new employee

		if (((String) fullTimeCombo.getSelectedItem()).equalsIgnoreCase("Yes"))
			fullTime = true;
		// create new Employee record with details from text fields
		theEmployee = new Employee(Integer.parseInt(idField.getText()), ppsField.getText().toUpperCase(), surnameField.getText().toUpperCase(),
				firstNameField.getText().toUpperCase(), genderCombo.getSelectedItem().toString().charAt(0),
				departmentCombo.getSelectedItem().toString(), Double.parseDouble(salaryField.getText()), fullTime);
		this.parent.currentEmployee = theEmployee;
		this.parent.addRecord(theEmployee);
		this.parent.displayRecords(theEmployee);
	}

	// check for input in text fields
	public boolean checkInput() {
		boolean valid = true;

		//Replaced with a constant that has a human-readable name explaining the meaning of the number.
		final Color BACKGROUND_COLOR = new Color(255,150,150);

		// if any of inputs are in wrong format, colour text field and display message
		if (ppsField.getText().equals("")) {
			ppsField.setBackground(BACKGROUND_COLOR);
			valid = false;
		}
		if (this.parent.correctPps(this.ppsField.getText().trim(), -1)) {
			ppsField.setBackground(BACKGROUND_COLOR);
			valid = false;
		}
		if (surnameField.getText().isEmpty()) {
			surnameField.setBackground(BACKGROUND_COLOR);
			valid = false;
		}
		if (firstNameField.getText().isEmpty()) {
			firstNameField.setBackground(BACKGROUND_COLOR);
			valid = false;
		}
		if (genderCombo.getSelectedIndex() == 0) {
			genderCombo.setBackground(BACKGROUND_COLOR);
			valid = false;
		}
		if (departmentCombo.getSelectedIndex() == 0) {
			departmentCombo.setBackground(BACKGROUND_COLOR);
			valid = false;
		}
		try {// try to get values from text field
			Double.parseDouble(salaryField.getText());
			// check if salary is greater than 0
			if (Double.parseDouble(salaryField.getText()) < 0) {
				salaryField.setBackground(BACKGROUND_COLOR);
				valid = false;
			}
		}
		catch (NumberFormatException num) {
			salaryField.setBackground(BACKGROUND_COLOR);
			valid = false;
		}
		if (fullTimeCombo.getSelectedIndex() == 0) {
			fullTimeCombo.setBackground(BACKGROUND_COLOR);
			valid = false;
		}
		return valid;
	}

	// set text field to white colour
	public void setToWhite() {

		//Replaced with a constant that has a human-readable name explaining the meaning of the number.
		//Allows background to be changed easily
		final Color WHITE_BACKGROUND = Color.WHITE;

		ppsField.setBackground(WHITE_BACKGROUND);
		surnameField.setBackground(WHITE_BACKGROUND);
		firstNameField.setBackground(WHITE_BACKGROUND);
		salaryField.setBackground(WHITE_BACKGROUND);
		genderCombo.setBackground(WHITE_BACKGROUND);
		departmentCombo.setBackground(WHITE_BACKGROUND);
		fullTimeCombo.setBackground(WHITE_BACKGROUND);
	}

	// action performed
	public void actionPerformed(ActionEvent e) {
		// if chosen option save, save record to file
		if (e.getSource() == save) {
			// if inputs correct, save record
			if (checkInput()) {
				addRecord();// add record to file
				dispose();// dispose dialog
				this.parent.changesMade = true;
			}
			// else display message and set text fields to white colour
			else {
				JOptionPane.showMessageDialog(null, "Wrong values or format! Please check!");
				setToWhite();
			}
		}
		else if (e.getSource() == cancel)
			dispose();// dispose dialog
	}
}