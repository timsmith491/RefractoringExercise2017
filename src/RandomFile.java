/*
 * 
 * This class is for accessing, creating and modifying records in a file
 * 
 * */


import java.io.IOException;
import java.io.RandomAccessFile;


import javax.swing.JOptionPane;

class RandomFile {
	private RandomAccessFile output;
	private RandomAccessFile input;

	// Create new file
	void createFile(String fileName) {
		RandomAccessFile file = null;

		try {
			file = new RandomAccessFile(fileName, "rw");

		}
		catch (IOException ioException) {
			JOptionPane.showMessageDialog(null, "Error processing file!");
			System.exit(1);
		}

		finally {
			try {
				if (file != null)
					file.close();
			}
			catch (IOException ioException) {
				JOptionPane.showMessageDialog(null, "Error closing file!");
				System.exit(1);
			}
		}
	}

	// Open file for adding or changing records
    void openWriteFile(String fileName) {
		try
		{
			output = new RandomAccessFile(fileName, "rw");
		}
		catch (IOException ioException) {
			JOptionPane.showMessageDialog(null, "File does not exist!");
		}
	}

	// Close file for adding or changing records
    void closeWriteFile() {
		try
		{
			if (output != null)
				output.close();
		}
		catch (IOException ioException) {
			JOptionPane.showMessageDialog(null, "Error closing file!");
			System.exit(1);
		}
	}


	long addRecords(Employee employeeToAdd) {
        long currentRecordStart = 0;

		RandomAccessEmployeeRecord record;

		try {
			record = new RandomAccessEmployeeRecord(employeeToAdd.getEmployeeId(), employeeToAdd.getPps(),
					employeeToAdd.getSurname(), employeeToAdd.getFirstName(), employeeToAdd.getGender(),
					employeeToAdd.getDepartment(), employeeToAdd.getSalary(), employeeToAdd.getFullTime());

			output.seek(output.length());// Look for proper position
			record.write(output);// Write object to file
			currentRecordStart = output.length();
		}
		catch (IOException ioException) {
			JOptionPane.showMessageDialog(null, "Error writing to file!");
		}

		return currentRecordStart - RandomAccessEmployeeRecord.SIZE;
	}

	// Change details for existing object
    void changeRecords(Employee newDetails, long byteToStart) {
        RandomAccessEmployeeRecord record;
        try
		{
			record = new RandomAccessEmployeeRecord(newDetails.getEmployeeId(), newDetails.getPps(),
					newDetails.getSurname(), newDetails.getFirstName(), newDetails.getGender(),
					newDetails.getDepartment(), newDetails.getSalary(), newDetails.getFullTime());

			output.seek(byteToStart);
			record.write(output);
		}
		catch (IOException ioException) {
			JOptionPane.showMessageDialog(null, "Error writing to file!");
		}
	}

	void deleteRecords(long byteToStart) {


        RandomAccessEmployeeRecord record;

		try {
			record = new RandomAccessEmployeeRecord();// Create empty object
			output.seek(byteToStart);// Look for proper position
			record.write(output);// Replace existing object with empty object
		}
		catch (IOException ioException) {
			JOptionPane.showMessageDialog(null, "Error writing to file!");
		}
	}

	void openReadFile(String fileName) {
		try {
			input = new RandomAccessFile(fileName, "r");
		}
		catch (IOException ioException) {
			JOptionPane.showMessageDialog(null, "File is not suported!");
		}
	}

	// Close file
    void closeReadFile() {
		try {
			if (input != null)
				input.close();
		}
		catch (IOException ioException) {
			JOptionPane.showMessageDialog(null, "Error closing file!");
			System.exit(1);
		}
	}
    

	// Get position of first record in file
    long getFirst() {
		long byteToStart = 0;

		try {
			input.length();
		}
		catch (IOException ignored) {
		}
		return byteToStart;
	}

	// Get position of last record in file
    long getLast() {
		long byteToStart = 0;
		try {
			byteToStart = input.length() - RandomAccessEmployeeRecord.SIZE;
		}
		catch (IOException ignored) {
		}
		return byteToStart;
	}


	long getNext(long readFrom) {
		long byteToStart = readFrom;

		try {
			input.seek(byteToStart);// Look for proper position in file
			// if next position is end of file go to start of file, else get next position
			if (byteToStart + RandomAccessEmployeeRecord.SIZE == input.length())
				byteToStart = 0;
			else
				byteToStart = byteToStart + RandomAccessEmployeeRecord.SIZE;
		}
		catch (NumberFormatException | IOException ignored) {
		}
        return byteToStart;
	}

	// Get position of previous record in file
    long getPrevious(long readFrom) throws NumberFormatException {
		long byteToStart = readFrom;

		try {
			input.seek(byteToStart);// Look for proper position in file
			// if previous position is start of file go to end of file, else get previous position
			if (byteToStart == 0)
				byteToStart = input.length() - RandomAccessEmployeeRecord.SIZE;
			else
				byteToStart = byteToStart - RandomAccessEmployeeRecord.SIZE;
		} catch (IOException ignored) {
		}
		return byteToStart;
	}

	// Get object from file in specified position
    Employee readRecords(long byteToStart) {
		Employee thisEmp;
		RandomAccessEmployeeRecord record = new RandomAccessEmployeeRecord();

		try {
			input.seek(byteToStart);
			record.read(input);
		}
		catch (IOException ignored) {
		}
		
		thisEmp = record;

		return thisEmp;
	}

	// Check if PPS Number already in use
    boolean isPpsExist(String pps, long currentByteStart) {
		RandomAccessEmployeeRecord record = new RandomAccessEmployeeRecord();
		boolean ppsExist = false;
        long currentByte = 0;

		try {
			// Start from start of file and loop until PPS Number is found or search returned to start position
			while (currentByte != input.length() && !ppsExist) {
				if (currentByte != currentByteStart) {
					input.seek(currentByte);
					record.read(input);
					if (record.getPps().trim().equalsIgnoreCase(pps)) {
						ppsExist = true;
						JOptionPane.showMessageDialog(null, "PPS number already exist!");
					}
				}
				currentByte = currentByte + RandomAccessEmployeeRecord.SIZE;
			}
		}
		catch (IOException ignored) {
		}

		return ppsExist;
	}

	boolean isSomeoneToDisplay() {
		boolean someoneToDisplay = false;
		long currentByte = 0;
		RandomAccessEmployeeRecord record = new RandomAccessEmployeeRecord();

		try {

			while (currentByte != input.length() && !someoneToDisplay) {
				input.seek(currentByte);
				record.read(input);
				if (record.getEmployeeId() > 0)
					someoneToDisplay = true;
				currentByte = currentByte + RandomAccessEmployeeRecord.SIZE;
			}
		}
		catch (IOException ignored) {
		}

		return someoneToDisplay;
	}
}
