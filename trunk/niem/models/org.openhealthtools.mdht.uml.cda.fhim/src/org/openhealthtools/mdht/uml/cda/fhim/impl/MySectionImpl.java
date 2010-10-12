/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.openhealthtools.mdht.uml.cda.fhim.impl;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EClass;
import org.openhealthtools.mdht.uml.cda.fhim.FHIMPackage;
import org.openhealthtools.mdht.uml.cda.fhim.MySection;
import org.openhealthtools.mdht.uml.cda.fhim.operations.MySectionOperations;
import org.openhealthtools.mdht.uml.cda.impl.SectionImpl;
import org.openhealthtools.mdht.uml.cda.util.CDAUtil;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>My Section</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class MySectionImpl extends SectionImpl implements MySection {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MySectionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FHIMPackage.Literals.MY_SECTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMySectionTemplateId(DiagnosticChain diagnostics, Map<Object, Object> context) {
		return MySectionOperations.validateMySectionTemplateId(this, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMySectionCode(DiagnosticChain diagnostics, Map<Object, Object> context) {
		return MySectionOperations.validateMySectionCode(this, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMySectionTitle(DiagnosticChain diagnostics, Map<Object, Object> context) {
		return MySectionOperations.validateMySectionTitle(this, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMySectionMyActivity(DiagnosticChain diagnostics, Map<Object, Object> context) {
		return MySectionOperations.validateMySectionMyActivity(this, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MySection init() {
    		CDAUtil.init(this);
    		return this;
	}
} //MySectionImpl
