package at.reisisoft.jku.ce.adaptivelearning.vaadin.ui.topic.accounting;

/*This file is part of the project "Reisisoft Adaptive Testing",
 * which is licenced under LGPL v3+. You may find a copy in the source,
 * or obtain one at http://www.gnu.org/licenses/lgpl-3.0-standalone.html */
import at.reisisoft.jku.ce.adaptivelearning.topic.accounting.AccountRecordData;
import at.reisisoft.jku.ce.adaptivelearning.topic.accounting.AccountingDataProvider;
import at.reisisoft.jku.ce.adaptivelearning.vaadin.input.AccountNumberInputField;
import at.reisisoft.jku.ce.adaptivelearning.vaadin.input.CurrencyTextBox;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

public class AccountingRecordInputFields extends GridLayout {

	private static final long serialVersionUID = 8543708812084425332L;
	private final AccountNumberInputField accountNumberInputField;
	private final ComboBox ddAccountNames;
	private final CurrencyTextBox currencyTextBox;

	public AccountingRecordInputFields(AccountRecordData data) {
		super(3, 1);
		AccountingDataProvider dataProvider = AccountingDataProvider
				.getInstance();
		setSpacing(true);
		// Add AccountNumber
		accountNumberInputField = new AccountNumberInputField();
		accountNumberInputField.setCaption("First 2 digits:");
		accountNumberInputField.setWidth("3em");
		addComponent(accountNumberInputField, 0, 0);
		// Add DD AccountNames
		ddAccountNames = new ComboBox("Account name:");
		ddAccountNames.addItems((Object[]) AccountingDataProvider.getInstance()
				.getAllAccountNames());
		ddAccountNames.setWidth("20em");
		addComponent(ddAccountNames, 1, 0);
		// Add Curreny field
		currencyTextBox = new CurrencyTextBox();
		currencyTextBox.setCaption("Figure (€):");
		currencyTextBox.setWidth("8em");
		addComponent(currencyTextBox, 2, 0);
		// set default values from AccountRecordData
		if (data.accountName != null) {
			if (!dataProvider.containsString(data.accountName)) {
				Notification.show('"' + data.accountName
						+ "\" is not a known account name",
						Type.WARNING_MESSAGE);
			}
			ddAccountNames.setValue(data.accountName);
			ddAccountNames.setEnabled(data.accountName == null
					|| data.accountName.length() == 0);
		}
		if (data.accountNumber > 0) {
			if (!dataProvider.containsNumber(data.accountNumber)) {
				Notification.show('"' + data.accountNumber
						+ "\" is not a known account number",
						Type.WARNING_MESSAGE);
			}
			accountNumberInputField.setValue(Integer
					.toString(data.accountNumber));
			accountNumberInputField.setEnabled(false);
		}
		if (data.value >= 0.01f) {
			currencyTextBox.setValue(Float.toString(data.value));
			currencyTextBox.setEnabled(false);
		}
	}

	public AccountRecordData getAccountRecordData() {
		return new AccountRecordData((String) ddAccountNames.getValue(),
				currencyTextBox.getNumericValue(),
				accountNumberInputField.getAccountNumber());
	}
}
