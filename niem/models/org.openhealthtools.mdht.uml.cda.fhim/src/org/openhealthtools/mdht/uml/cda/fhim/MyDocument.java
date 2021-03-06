/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.openhealthtools.mdht.uml.cda.fhim;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.openhealthtools.mdht.uml.cda.ccd.ContinuityOfCareDocument;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>My Document</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.openhealthtools.mdht.uml.cda.fhim.FHIMPackage#getMyDocument()
 * @model extendedMetaData="name='ClinicalDocument'"
 *        annotation="http://www.openhealthtools.org/mdht/uml/cda/annotation templateId.root='1.2.3.4' constraints.validation.error='MyDocumentTemplateId MyDocumentMySection'"
 * @generated
 */
public interface MyDocument extends ContinuityOfCareDocument {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * self.templateId->exists(id : datatypes::II | id.root = '1.2.3.4')
	 * @param diagnostics The chain of diagnostics to which problems are to be appended.
	 * @param context The cache of context-specific information.
	 * <!-- end-model-doc -->
	 * @model annotation="http://www.eclipse.org/uml2/1.1.0/GenModel body='self.templateId->exists(id : datatypes::II | id.root = \'1.2.3.4\')'"
	 * @generated
	 */
	boolean validateMyDocumentTemplateId(DiagnosticChain diagnostics, Map<Object, Object> context);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * self.getSections()->one(section : cda::Section | section.oclIsKindOf(fhim::MySection))
	 * @param diagnostics The chain of diagnostics to which problems are to be appended.
	 * @param context The cache of context-specific information.
	 * <!-- end-model-doc -->
	 * @model annotation="http://www.eclipse.org/uml2/1.1.0/GenModel body='self.getSections()->one(section : cda::Section | section.oclIsKindOf(fhim::MySection))'"
	 * @generated
	 */
	boolean validateMyDocumentMySection(DiagnosticChain diagnostics, Map<Object, Object> context);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MyDocument init();
} // MyDocument
