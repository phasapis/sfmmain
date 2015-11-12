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
 * Source Class: DefaultDiscrete_Model <br>
 * @version generated on Mon May 11 16:25:20 BST 2015 by jbjares
 */
public class DefaultDiscrete_Model extends WrappedIndividualImpl implements Discrete_Model {

    public DefaultDiscrete_Model(CodeGenerationInference inference, IRI iri) {
        super(inference, iri);
    }





    /* ***************************************************
     * Object Property http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#hasDomain
     */
     
    public Collection<? extends Finite_Element_Model_Domain> getHasDomain() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_HASDOMAIN,
                                               DefaultFinite_Element_Model_Domain.class);
    }

    public boolean hasHasDomain() {
	   return !getHasDomain().isEmpty();
    }

    public void addHasDomain(Finite_Element_Model_Domain newHasDomain) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_HASDOMAIN,
                                       newHasDomain);
    }

    public void removeHasDomain(Finite_Element_Model_Domain oldHasDomain) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_HASDOMAIN,
                                          oldHasDomain);
    }


    /* ***************************************************
     * Object Property http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#hasMesh
     */
     
    public Collection<? extends Mesh> getHasMesh() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_HASMESH,
                                               DefaultMesh.class);
    }

    public boolean hasHasMesh() {
	   return !getHasMesh().isEmpty();
    }

    public void addHasMesh(Mesh newHasMesh) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_HASMESH,
                                       newHasMesh);
    }

    public void removeHasMesh(Mesh oldHasMesh) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_HASMESH,
                                          oldHasMesh);
    }


    /* ***************************************************
     * Object Property http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#hasPerformedOnIt
     */
     
    public Collection<? extends WrappedIndividual> getHasPerformedOnIt() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_HASPERFORMEDONIT,
                                               WrappedIndividualImpl.class);
    }

    public boolean hasHasPerformedOnIt() {
	   return !getHasPerformedOnIt().isEmpty();
    }

    public void addHasPerformedOnIt(WrappedIndividual newHasPerformedOnIt) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_HASPERFORMEDONIT,
                                       newHasPerformedOnIt);
    }

    public void removeHasPerformedOnIt(WrappedIndividual oldHasPerformedOnIt) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_HASPERFORMEDONIT,
                                          oldHasPerformedOnIt);
    }


    /* ***************************************************
     * Object Property http://www.sifemontologies.com/ontologies/FiniteElementModel.owl#idealizes
     */
     
    public Collection<? extends Physical_System> getIdealizes() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_IDEALIZES,
                                               DefaultPhysical_System.class);
    }

    public boolean hasIdealizes() {
	   return !getIdealizes().isEmpty();
    }

    public void addIdealizes(Physical_System newIdealizes) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_IDEALIZES,
                                       newIdealizes);
    }

    public void removeIdealizes(Physical_System oldIdealizes) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_IDEALIZES,
                                          oldIdealizes);
    }


}
