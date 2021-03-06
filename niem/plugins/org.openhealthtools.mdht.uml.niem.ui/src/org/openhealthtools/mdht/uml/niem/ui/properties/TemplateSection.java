/*******************************************************************************
 * Copyright (c) 2009 David A Carlson.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     David A Carlson (XMLmodeling.com) - initial API and implementation
 *     
 * $Id$
 *******************************************************************************/
package org.openhealthtools.mdht.uml.niem.ui.properties;


import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.emf.workspace.AbstractEMFOperation;
import org.eclipse.emf.workspace.IWorkspaceCommandStack;
import org.eclipse.gmf.runtime.diagram.ui.properties.sections.AbstractModelerPropertySection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Stereotype;
import org.openhealthtools.mdht.uml.cda.core.util.CDAProfileUtil;
import org.openhealthtools.mdht.uml.cda.core.util.ICDAProfileConstants;
import org.openhealthtools.mdht.uml.niem.ui.internal.Logger;

/**
 * The profile properties section for CDA Template.
 */
public class TemplateSection extends AbstractModelerPropertySection {

	private NamedElement namedElement;

	private Text templateIdText;
	private boolean templateIdModified = false;
	private Text assigningAuthorityText;
	private boolean assigningAuthorityModified = false;

    private ModifyListener modifyListener = new ModifyListener() {
		public void modifyText(final ModifyEvent event) {
			if (templateIdText == event.getSource()) {
				templateIdModified = true;
			}
			if (assigningAuthorityText == event.getSource()) {
				assigningAuthorityModified = true;
			}
		}
	};

	private KeyListener keyListener = new KeyListener() {
		public void keyPressed(KeyEvent e) {
			// do nothing
		}

		public void keyReleased(KeyEvent e) {
			if (SWT.CR == e.character || SWT.KEYPAD_CR == e.character)
				modifyFields();
		}
	};
	
	private FocusListener focusListener = new FocusListener() {
		public void focusGained(FocusEvent e) {
			// do nothing
		}

		public void focusLost(FocusEvent event) {
			modifyFields();
		}
	};
	
	private void modifyFields() {
		if (!(templateIdModified || assigningAuthorityModified)) {
			return;
		}
		
		try {
			TransactionalEditingDomain editingDomain = 
				TransactionUtil.getEditingDomain(namedElement);
			
			IUndoableOperation operation = new AbstractEMFOperation(editingDomain, "temp") {
			    protected IStatus doExecute(IProgressMonitor monitor, IAdaptable info) {
					Stereotype stereotype = CDAProfileUtil.getAppliedCDAStereotype(
							namedElement, ICDAProfileConstants.CDA_TEMPLATE);
					
					if (stereotype == null) {
						stereotype = CDAProfileUtil.applyCDAStereotype(
								namedElement, ICDAProfileConstants.CDA_TEMPLATE);
					}
					if (templateIdModified) {
						templateIdModified = false;
						this.setLabel("Set CDA Template ID");

						if (stereotype != null) {
							String value = templateIdText.getText().trim();
							namedElement.setValue(stereotype, 
									ICDAProfileConstants.CDA_TEMPLATE_TEMPLATE_ID,
									value.length()>0 ? value : null);

						}
					}
					else if (assigningAuthorityModified) {
						assigningAuthorityModified = false;
						this.setLabel("Set CDA Template Authority");

						if (stereotype != null) {
							String value = assigningAuthorityText.getText().trim();
							namedElement.setValue(stereotype, 
									ICDAProfileConstants.CDA_TEMPLATE_ASSIGNING_AUTHORITY_NAME,
									value.length()>0 ? value : null);

						}
					}
					else {
						return Status.CANCEL_STATUS;
					}
					
			        return Status.OK_STATUS;
			    }};

		    try {
				IWorkspaceCommandStack commandStack = (IWorkspaceCommandStack) editingDomain.getCommandStack();
				operation.addContext(commandStack.getDefaultUndoContext());
		        commandStack.getOperationHistory().execute(operation, new NullProgressMonitor(), getPart());
		        
		    } catch (ExecutionException ee) {
		        Logger.logException(ee);
		    }
		    
		} catch (Exception e) {
			throw new RuntimeException(e.getCause());
		}
	}

	public void createControls(final Composite parent,
			final TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(parent, aTabbedPropertySheetPage);
		
		Composite composite = getWidgetFactory()
				.createGroup(parent, "CDA Template");
        FormLayout layout = new FormLayout();
        layout.marginWidth = ITabbedPropertyConstants.HSPACE + 2;
        layout.marginHeight = ITabbedPropertyConstants.VSPACE;
        layout.spacing = ITabbedPropertyConstants.VMARGIN + 1;
        composite.setLayout(layout);
		
		FormData data = null;

		templateIdText = getWidgetFactory().createText(composite, ""); //$NON-NLS-1$
		CLabel templateIdLabel = getWidgetFactory()
				.createCLabel(composite, "ID:"); //$NON-NLS-1$
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.top = new FormAttachment(templateIdText, 0, SWT.CENTER);
		templateIdLabel.setLayoutData(data);

		data = new FormData();
		data.left = new FormAttachment(templateIdLabel, 0);
		data.width = 200;
		data.top = new FormAttachment(0,2, ITabbedPropertyConstants.VSPACE);
		templateIdText.setLayoutData(data);

		assigningAuthorityText = getWidgetFactory().createText(composite, ""); //$NON-NLS-1$
		CLabel assigningAuthorityLabel = getWidgetFactory()
				.createCLabel(composite, "Assigning Authority:"); //$NON-NLS-1$
		data = new FormData();
		data.left = new FormAttachment(templateIdText, ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(assigningAuthorityText, 0, SWT.CENTER);
		assigningAuthorityLabel.setLayoutData(data);

		data = new FormData();
		data.left = new FormAttachment(assigningAuthorityLabel, 0);
		data.width = 200;
		data.top = new FormAttachment(0,2, ITabbedPropertyConstants.VSPACE);
		assigningAuthorityText.setLayoutData(data);

	}

	protected boolean isReadOnly() {
		if (namedElement != null) {
			TransactionalEditingDomain editingDomain = 
				TransactionUtil.getEditingDomain(namedElement);
			if (editingDomain != null && editingDomain.isReadOnly(namedElement.eResource())) {
				return true;
			}
		}

		return super.isReadOnly();
	}

	/*
	 * Override super implementation to allow for objects that are not IAdaptable.
	 * 
	 * (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.diagram.ui.properties.sections.AbstractModelerPropertySection#addToEObjectList(java.lang.Object)
	 */
	protected boolean addToEObjectList(Object object) {
		boolean added = super.addToEObjectList(object);
		if (!added && object instanceof Element) {
			getEObjectList().add(object);
			added = true;
		}
		return added;
	}

	public void setInput(IWorkbenchPart part, ISelection selection) {
		super.setInput(part, selection);
		EObject element = getEObject();
		Assert.isTrue(element instanceof NamedElement);
		this.namedElement = (NamedElement) element;
	}

	public void dispose() {
		super.dispose();

	}

	public void refresh() {
		Stereotype stereotype = CDAProfileUtil.getAppliedCDAStereotype(
				namedElement, ICDAProfileConstants.CDA_TEMPLATE);

		templateIdText.removeModifyListener(modifyListener);
		templateIdText.removeKeyListener(keyListener);
		templateIdText.removeFocusListener(focusListener);
		if (stereotype != null) {
			String id = (String) namedElement.getValue(stereotype, ICDAProfileConstants.CDA_TEMPLATE_TEMPLATE_ID);
			templateIdText.setText(id!=null ? id : "");
		}
		else {
			templateIdText.setText("");
		}
		templateIdText.addModifyListener(modifyListener);
		templateIdText.addKeyListener(keyListener);
		templateIdText.addFocusListener(focusListener);

		assigningAuthorityText.removeModifyListener(modifyListener);
		assigningAuthorityText.removeKeyListener(keyListener);
		assigningAuthorityText.removeFocusListener(focusListener);
		if (stereotype != null) {
			String id = (String) namedElement.getValue(stereotype, ICDAProfileConstants.CDA_TEMPLATE_ASSIGNING_AUTHORITY_NAME);
			assigningAuthorityText.setText(id!=null ? id : "");
		}
		else {
			assigningAuthorityText.setText("");
		}
		assigningAuthorityText.addModifyListener(modifyListener);
		assigningAuthorityText.addKeyListener(keyListener);
		assigningAuthorityText.addFocusListener(focusListener);

		if (isReadOnly()) {
			templateIdText.setEnabled(false);
			assigningAuthorityText.setEnabled(false);
		}
		else {
			templateIdText.setEnabled(true);
			assigningAuthorityText.setEnabled(true);
		}

	}

	/**
	 * Update if necessary, upon receiving the model event.
	 * 
	 * @see #aboutToBeShown()
	 * @see #aboutToBeHidden()
	 * @param notification -
	 *            even notification
	 * @param element -
	 *            element that has changed
	 */
	public void update(final Notification notification, EObject element) {
		if (!isDisposed()) {
			postUpdateRequest(new Runnable() {

				public void run() {
					// widget not disposed and UML element is not deleted
					if (!isDisposed() && namedElement.eResource() != null)
						refresh();
				}
			});
		}
	}

}
