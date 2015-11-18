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
 * Source Class: DefaultArgumentFunctionPair <br>
 * @version generated on Mon May 11 16:25:19 BST 2015 by jbjares
 */
public class DefaultArgumentFunctionPair extends WrappedIndividualImpl implements ArgumentFunctionPair {

    public DefaultArgumentFunctionPair(CodeGenerationInference inference, IRI iri) {
        super(inference, iri);
    }





    /* ***************************************************
     * Object Property http://www.sifemontologies.com/ontologies/FEMSettingsPAK.owl#isArgumentFunctionPairOf
     */
     
    public Collection<? extends Solver> getIsArgumentFunctionPairOf() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_ISARGUMENTFUNCTIONPAIROF,
                                               DefaultSolver.class);
    }

    public boolean hasIsArgumentFunctionPairOf() {
	   return !getIsArgumentFunctionPairOf().isEmpty();
    }

    public void addIsArgumentFunctionPairOf(Solver newIsArgumentFunctionPairOf) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_ISARGUMENTFUNCTIONPAIROF,
                                       newIsArgumentFunctionPairOf);
    }

    public void removeIsArgumentFunctionPairOf(Solver oldIsArgumentFunctionPairOf) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_ISARGUMENTFUNCTIONPAIROF,
                                          oldIsArgumentFunctionPairOf);
    }


    /* ***************************************************
     * Data Property http://www.sifemontologies.com/ontologies/FEMSettingsPAK.owl#hasArgument
     */
     
    public Collection<? extends Object> getHasArgument() {
		return getDelegate().getPropertyValues(getOwlIndividual(), Vocabulary.DATA_PROPERTY_HASARGUMENT, Object.class);
    }

    public boolean hasHasArgument() {
		return !getHasArgument().isEmpty();
    }

    public void addHasArgument(Object newHasArgument) {
	    getDelegate().addPropertyValue(getOwlIndividual(), Vocabulary.DATA_PROPERTY_HASARGUMENT, newHasArgument);
    }

    public void removeHasArgument(Object oldHasArgument) {
		getDelegate().removePropertyValue(getOwlIndividual(), Vocabulary.DATA_PROPERTY_HASARGUMENT, oldHasArgument);
    }


    /* ***************************************************
     * Data Property http://www.sifemontologies.com/ontologies/FEMSettingsPAK.owl#hasArgumentFunctionPairIndex
     */
     
    public Collection<? extends Integer> getHasArgumentFunctionPairIndex() {
		return getDelegate().getPropertyValues(getOwlIndividual(), Vocabulary.DATA_PROPERTY_HASARGUMENTFUNCTIONPAIRINDEX, Integer.class);
    }

    public boolean hasHasArgumentFunctionPairIndex() {
		return !getHasArgumentFunctionPairIndex().isEmpty();
    }

    public void addHasArgumentFunctionPairIndex(Integer newHasArgumentFunctionPairIndex) {
	    getDelegate().addPropertyValue(getOwlIndividual(), Vocabulary.DATA_PROPERTY_HASARGUMENTFUNCTIONPAIRINDEX, newHasArgumentFunctionPairIndex);
    }

    public void removeHasArgumentFunctionPairIndex(Integer oldHasArgumentFunctionPairIndex) {
		getDelegate().removePropertyValue(getOwlIndividual(), Vocabulary.DATA_PROPERTY_HASARGUMENTFUNCTIONPAIRINDEX, oldHasArgumentFunctionPairIndex);
    }


    /* ***************************************************
     * Data Property http://www.sifemontologies.com/ontologies/FEMSettingsPAK.owl#hasFunction
     */
     
    public Collection<? extends Object> getHasFunction() {
		return getDelegate().getPropertyValues(getOwlIndividual(), Vocabulary.DATA_PROPERTY_HASFUNCTION, Object.class);
    }

    public boolean hasHasFunction() {
		return !getHasFunction().isEmpty();
    }

    public void addHasFunction(Object newHasFunction) {
	    getDelegate().addPropertyValue(getOwlIndividual(), Vocabulary.DATA_PROPERTY_HASFUNCTION, newHasFunction);
    }

    public void removeHasFunction(Object oldHasFunction) {
		getDelegate().removePropertyValue(getOwlIndividual(), Vocabulary.DATA_PROPERTY_HASFUNCTION, oldHasFunction);
    }


}