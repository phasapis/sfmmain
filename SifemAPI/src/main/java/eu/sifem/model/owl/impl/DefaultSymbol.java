package eu.sifem.model.owl.impl;

import eu.sifem.model.owl.*;

import java.util.Collection;

import org.protege.owl.codegeneration.WrappedIndividual;
import org.protege.owl.codegeneration.impl.WrappedIndividualImpl;

import org.protege.owl.codegeneration.inference.CodeGenerationInference;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;


/**
 * Generated by Protege (http://protege.stanford.edu).<br>
 * Source Class: DefaultSymbol <br>
 * @version generated on Mon May 11 16:25:19 BST 2015 by jbjares
 */
public class DefaultSymbol extends WrappedIndividualImpl implements Symbol {

    public DefaultSymbol(CodeGenerationInference inference, IRI iri) {
        super(inference, iri);
    }





    /* ***************************************************
     * Object Property http://codata.jp/OML-Property.owl#PropertyName
     */
     
    public Collection<? extends Property> getPropertyName() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_PROPERTYNAME,
                                               DefaultProperty.class);
    }

    public boolean hasPropertyName() {
	   return !getPropertyName().isEmpty();
    }

    public void addPropertyName(Property newPropertyName) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_PROPERTYNAME,
                                       newPropertyName);
    }

    public void removePropertyName(Property oldPropertyName) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_PROPERTYNAME,
                                          oldPropertyName);
    }


    /* ***************************************************
     * Data Property http://codata.jp/OML-Property.owl#Eq
     */
     
    public Collection<? extends Object> getEq() {
		return getDelegate().getPropertyValues(getOwlIndividual(), Vocabulary.DATA_PROPERTY_EQ, Object.class);
    }

    public boolean hasEq() {
		return !getEq().isEmpty();
    }

    public void addEq(Object newEq) {
	    getDelegate().addPropertyValue(getOwlIndividual(), Vocabulary.DATA_PROPERTY_EQ, newEq);
    }

    public void removeEq(Object oldEq) {
		getDelegate().removePropertyValue(getOwlIndividual(), Vocabulary.DATA_PROPERTY_EQ, oldEq);
    }


    /* ***************************************************
     * Data Property http://codata.jp/OML-Property.owl#Notation
     */
     
    public Collection<? extends Object> getNotation() {
		return getDelegate().getPropertyValues(getOwlIndividual(), Vocabulary.DATA_PROPERTY_NOTATION, Object.class);
    }

    public boolean hasNotation() {
		return !getNotation().isEmpty();
    }

    public void addNotation(Object newNotation) {
	    getDelegate().addPropertyValue(getOwlIndividual(), Vocabulary.DATA_PROPERTY_NOTATION, newNotation);
    }

    public void removeNotation(Object oldNotation) {
		getDelegate().removePropertyValue(getOwlIndividual(), Vocabulary.DATA_PROPERTY_NOTATION, oldNotation);
    }


}