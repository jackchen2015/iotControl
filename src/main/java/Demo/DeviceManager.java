package Demo;

import java.awt.*;
import javax.swing.*;

import Demo.AppTask.DeviceBasicInfo;

import java.awt.event.*;
import java.util.Vector;

public class DeviceManager extends ManagerModule {
	static void updatePanel(JPanel pManager, AppTask app, LogPrinter log) {
		pManager.removeAll();
		pManager.setBorder(BorderFactory.createTitledBorder("Device Manager"));

		// Layout
		pManager.setLayout(new GridBagLayout());
		GridBagConstraints constraint = new GridBagConstraints();
		constraint.fill = GridBagConstraints.NONE;
		constraint.anchor = GridBagConstraints.WEST;
		constraint.gridx = 0;
		constraint.gridy = 0;
		constraint.gridwidth = 1;
		constraint.gridheight = 1;

		new DeviceManager(pManager, constraint, app, log);
	}

	int mItem = 0;

	// Constructor
	DeviceManager(JPanel pManager, GridBagConstraints constraint, AppTask app, LogPrinter log) {
		super(app, log);

		// Register & Delete Panel
		createRegisterAndDeletePanel(pManager, constraint);
		// Modify Panel
		createModifyPanel(pManager, constraint);
		// Get List Panel
		createGetListPanel(pManager, constraint);
		// Query Panel
		createQueryPanel(pManager, constraint);
	}

	void createRegisterAndDeletePanel(JPanel pManager, GridBagConstraints constraint) {
		// Register & Delete Device Panel
		JPanel pRegisterAndDelete = new JPanel();
		pRegisterAndDelete.setLayout(new FlowLayout());
		constraint.gridy = mItem;
		++mItem;
		pManager.add(pRegisterAndDelete, constraint);

		// Add "Register Direct-Connection Device" Item
		addRegisterDirectConnectionDeviceItem(pRegisterAndDelete);
		// Add "Delete Device" Item
		addDeleteDeviceItem(pRegisterAndDelete);
	}

	void createModifyPanel(JPanel pManager, GridBagConstraints constraint) {
		// Modify Device Info Panel
		JPanel pModify = new JPanel();
		pModify.setLayout(new FlowLayout());
		constraint.gridy = mItem;
		++mItem;
		pManager.add(pModify, constraint);

		// Add "Modify Device Info" Item
		addModifyDeviceInfoItem(pModify);
	}

	void createGetListPanel(JPanel pManager, GridBagConstraints constraint) {
		// Get List Panel
		JPanel pGetList = new JPanel();
		pGetList.setLayout(new FlowLayout());
		constraint.gridy = mItem;
		++mItem;
		pManager.add(pGetList, constraint);

		// Add "Get Device List" Item
		addGetListItem(pGetList);
	}

	void createQueryPanel(JPanel pManager, GridBagConstraints constraint) {
		// Query Device Panel
		JPanel pQuery = new JPanel();
		pQuery.setLayout(new FlowLayout());
		constraint.gridy = mItem;
		++mItem;
		pManager.add(pQuery, constraint);

		// Add "Query Device Status" Item
		addQueryDeviceStatusItem(pQuery);
		// Add "Query Device Basic Info" Item
		addQueryDeviceBasicInfoItem(pQuery);
	}

	void addRegisterDirectConnectionDeviceItem(JPanel pCurPanel) {
		// Register Direct-Connection Device
		JPanel pRegister = new JPanel();
		pRegister.setLayout(new FlowLayout());
		pRegister.setBorder(BorderFactory.createTitledBorder("Register Direct Device:"));
		pCurPanel.add(pRegister);

		// Node ID
		pRegister.add(new JLabel("Node ID:"));
		final JTextField tfNodeID = new JTextField(MainWin.TEXT_FIELD_LONG);
		pRegister.add(tfNodeID);
		// Verify Code
		pRegister.add(new JLabel("Verify Code(O):"));
		final JTextField tfVerifyCode = new JTextField(MainWin.TEXT_FIELD_LONG);
		pRegister.add(tfVerifyCode);
		// Timeout Setting
		pRegister.add(new JLabel("Time out(O):"));
		final JTextField tfTimeout = new JTextField(MainWin.TEXT_FIELD_SHORT);
		pRegister.add(tfTimeout);
		// Register Button
		JButton bRegister = new JButton("Register");
		pRegister.add(bRegister);

		// Button Action
		bRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mLogPrinter.printlnAsTitle("Register Direct Device");
				// Get Input Data
				String strNodeId = tfNodeID.getText();
				String strVerifyCode = tfVerifyCode.getText();
				String strTimeout = tfTimeout.getText();
				int nTimeout = 0;
				if (strTimeout != null && strTimeout.length() != 0) {
					nTimeout = Integer.parseInt(strTimeout);
				}
				mLogPrinter.printlnAsParam("Node ID: " + strNodeId + ", Verify Code: " + strVerifyCode + ", Timeout: " + nTimeout);
				// Application Operation
				try {
					String strDeviceId = mAppTask.registerDirectDevice(strNodeId, strVerifyCode, nTimeout);
					if (strDeviceId != null) {
						mLogPrinter.printlnAsResult("Register Direct Device Success, Device ID: " + strDeviceId);
					} else {
						mLogPrinter.printlnAsError("Register Direct Device Failed.");
					}
				} catch (Exception e) {
					mLogPrinter.printExceptionTrace(e);
					mLogPrinter.printlnAsError("Exception Was Caught While Register Direct-Connection Device.");
				}
				// Clear Text Field
				tfNodeID.setText("");
				tfVerifyCode.setText("");
				tfTimeout.setText("");
			}
		});
	}

	void addDeleteDeviceItem(JPanel pCurPanel) {
		// Delete Device
		JPanel pDelete = new JPanel();
		pDelete.setLayout(new FlowLayout());
		pDelete.setBorder(BorderFactory.createTitledBorder("Delete Device:"));
		pCurPanel.add(pDelete);

		// Device ID
		pDelete.add(new JLabel("Device ID:"));
		final JTextField tfDeviceId = new JTextField(MainWin.TEXT_FIELD_LONG);
		pDelete.add(tfDeviceId);
		// Delete Button
		JButton bDelete = new JButton("Delete");
		pDelete.add(bDelete);

		// Button Action
		bDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mLogPrinter.printlnAsTitle("Delete Device");
				// Get Input Data
				String strDeviceId = tfDeviceId.getText();
				mLogPrinter.printlnAsParam("Device ID: " + strDeviceId);
				// Application Operation
				try {
					boolean bRet = mAppTask.deleteDevice(strDeviceId);
					if (bRet) {
						mLogPrinter.printlnAsResult("Delete Device Success.");
					} else {
						mLogPrinter.printlnAsError("Delete Device Failed.");
					}
				} catch (Exception e) {
					mLogPrinter.printExceptionTrace(e);
					mLogPrinter.printlnAsError("Exception Was Caught While Delete Device.");
				}
				// Clear Text Field
				tfDeviceId.setText("");
			}
		});
	}

	void addModifyDeviceInfoItem(JPanel pCurPanel) {
		// Modify Device Info
		JPanel pModify = new JPanel();
		pModify.setLayout(new FlowLayout());
		pModify.setBorder(BorderFactory.createTitledBorder("Modify Device Info:"));
		pCurPanel.add(pModify);

		// Device ID
		pModify.add(new JLabel("Device ID:"));
		final JTextField tfDeviceId = new JTextField(MainWin.TEXT_FIELD_LONG);
		pModify.add(tfDeviceId);
		// Manufacturer ID
		pModify.add(new JLabel("Manufacturer ID:"));
		final JTextField tfManufacturerId = new JTextField(MainWin.TEXT_FIELD_SHORT);
		pModify.add(tfManufacturerId);
		// Manufacturer Name
		pModify.add(new JLabel("Manufacturer Name:"));
		final JTextField tfManufacturerName = new JTextField(MainWin.TEXT_FIELD_LONG);
		pModify.add(tfManufacturerName);
		// Device Type
		pModify.add(new JLabel("Device Type:"));
		final JTextField tfDeviceType = new JTextField(MainWin.TEXT_FIELD_SHORT);
		pModify.add(tfDeviceType);
		// Model
		pModify.add(new JLabel("Model:"));
		final JTextField tfModel = new JTextField(MainWin.TEXT_FIELD_SHORT);
		pModify.add(tfModel);
		// Protocol Type
		pModify.add(new JLabel("Protocol Type:"));
		final JTextField tfProtocolType = new JTextField(MainWin.TEXT_FIELD_SHORT);
		pModify.add(tfProtocolType);
		// Modify Button
		JButton bModify = new JButton("Modify");
		pModify.add(bModify);

		// Button Action
		bModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mLogPrinter.printlnAsTitle("Modify Device Info");
				// Get Input Data
				String strDeviceId = tfDeviceId.getText();
				String strManufacturerId = tfManufacturerId.getText();
				String strManufacturerName = tfManufacturerName.getText();
				String strDeviceType = tfDeviceType.getText();
				String strModel = tfModel.getText();
				String strProtocolType = tfProtocolType.getText();
				mLogPrinter.printlnAsParam("Device ID: " + strDeviceId + ", Device Type: " + strDeviceType
											+ ", Manufacturer ID: " + strManufacturerId + ", Manufacturer Name: " + strManufacturerName
											+ ", Model: " + strModel + ", Protocol Type: " + strProtocolType);
				// Application Operation
				try {
					boolean bRet = mAppTask.modifyDeviceInfo(strDeviceId, strManufacturerId, strManufacturerName,
																strModel, strProtocolType, strDeviceType);
					if (bRet) {
						mLogPrinter.printlnAsResult("Modify Device Info Success.");
						
					} else {
						mLogPrinter.printlnAsError("Modify Device Info Failed.");
					}
				} catch (Exception e) {
					mLogPrinter.printExceptionTrace(e);
					mLogPrinter.printlnAsError("Exception Was Caught While Modify Device Info.");
				}
				// Clear Text Field
				tfDeviceId.setText("");
				tfManufacturerId.setText("");
				tfManufacturerName.setText("");
				tfModel.setText("");
				tfProtocolType.setText("");
				tfDeviceType.setText("");
			}
		});
	}

	void addGetListItem(JPanel pCurPanel) {
		// Modify Device Info
		JPanel pGetList = new JPanel();
		pGetList.setLayout(new FlowLayout());
		pGetList.setBorder(BorderFactory.createTitledBorder("Get Device List:"));
		pCurPanel.add(pGetList);

		// Node Type
		pGetList.add(new JLabel("Node Type:"));
		String[] aNodeTypeContent = {"ALL", "ENDPOINT", "GATEWAY"};
		final JComboBox<String> boxNodeType = new JComboBox<String>(aNodeTypeContent);
		pGetList.add(boxNodeType);
		// Status
		pGetList.add(new JLabel("Status:"));
		String[] aStatusContent = {"ALL", "ONLINE", "OFFLINE"};
		final JComboBox<String> boxStatus = new JComboBox<String>(aStatusContent);
		pGetList.add(boxStatus);
		// Gateway ID
		pGetList.add(new JLabel("Gateway ID(O):"));
		final JTextField tfGatewayId = new JTextField(MainWin.TEXT_FIELD_LONG);
		pGetList.add(tfGatewayId);
		// Get List Button
		JButton bGetList = new JButton("Get Device List");
		pGetList.add(bGetList);

		// Button Action
		bGetList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mLogPrinter.printlnAsTitle("Get Device List");
				// Get Input Data
				String strNodeType = boxNodeType.getSelectedItem().toString();
				String strStatus = boxStatus.getSelectedItem().toString();
				String strGatewayId = tfGatewayId.getText();
				mLogPrinter.printlnAsParam("Node Type: " + strNodeType + ", Status: " + strStatus + ", Gateway ID: " + strGatewayId);
				// Application Operation
				try {
					Vector<String> vList = mAppTask.getDeviceList(strNodeType, strStatus, strGatewayId);
					// Print Device List
					mLogPrinter.printDevicesList(vList);
				} catch (Exception e) {
					mLogPrinter.printExceptionTrace(e);
					mLogPrinter.printlnAsError("Exception Was Caught While Get Device List.");
				}
				// Clear Text Field
				tfGatewayId.setText("");
			}
		});
	}

	void addQueryDeviceStatusItem(JPanel pCurPanel) {
		// Query Device Status
		JPanel pQueryDeviceStatus = new JPanel();
		pQueryDeviceStatus.setLayout(new FlowLayout());
		pQueryDeviceStatus.setBorder(BorderFactory.createTitledBorder("Query Device Status:"));
		pCurPanel.add(pQueryDeviceStatus);

		// Device ID
		pQueryDeviceStatus.add(new JLabel("Device ID:"));
		final JTextField tfDeviceId = new JTextField(MainWin.TEXT_FIELD_LONG);
		pQueryDeviceStatus.add(tfDeviceId);
		// Check Button
		JButton bCheckStatus = new JButton("Check Status");
		pQueryDeviceStatus.add(bCheckStatus);

		// Button Action
		bCheckStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mLogPrinter.printlnAsTitle("Check Device Status");
				// Get Input Data
				String strDeviceId = tfDeviceId.getText();
				mLogPrinter.printlnAsParam("Device ID: " + strDeviceId);
				// Application Operation
				try {
					DeviceBasicInfo info = mAppTask.queryDeviceBasicInfo(strDeviceId);
					if (info != null && info.mStrStatus.equals("ONLINE")) {
						mLogPrinter.printlnAsResult("Device Status: ONLINE.");
					} else {
						mLogPrinter.printlnAsResult("Device Status: OFFLINE.");
					}
				} catch (Exception e) {
					mLogPrinter.printExceptionTrace(e);
					mLogPrinter.printlnAsError("Exception Was Caught While Check Device Status.");
				}
				// Clear Text Field
				tfDeviceId.setText("");
			}
		});
	}

	void addQueryDeviceBasicInfoItem(JPanel pCurPanel) {
		// Query Device Status
		JPanel pQueryDeviceBasicInfo = new JPanel();
		pQueryDeviceBasicInfo.setLayout(new FlowLayout());
		pQueryDeviceBasicInfo.setBorder(BorderFactory.createTitledBorder("Query Device Basic Info:"));
		pCurPanel.add(pQueryDeviceBasicInfo);

		// Device ID
		pQueryDeviceBasicInfo.add(new JLabel("Device ID:"));
		final JTextField tfDeviceId = new JTextField(MainWin.TEXT_FIELD_LONG);
		pQueryDeviceBasicInfo.add(tfDeviceId);
		// Check Button
		JButton bCheckStatus = new JButton("Query Basic Info");
		pQueryDeviceBasicInfo.add(bCheckStatus);

		// Button Action
		bCheckStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mLogPrinter.printlnAsTitle("Query Device Basic Info");
				// Get Input Data
				String strDeviceId = tfDeviceId.getText();
				mLogPrinter.printlnAsParam("Device ID: " + strDeviceId);
				// Application Operation
				try {
					DeviceBasicInfo info = mAppTask.queryDeviceBasicInfo(strDeviceId);
					// Print Basic Info
					mLogPrinter.printDeviceBasicInfo(info);
				} catch (Exception e) {
					mLogPrinter.printExceptionTrace(e);
					mLogPrinter.printlnAsError("Exception Was Caught While Query Device Basic Info.");
				}
				// Clear Text Field
				tfDeviceId.setText("");
			}
		});
	}
}
