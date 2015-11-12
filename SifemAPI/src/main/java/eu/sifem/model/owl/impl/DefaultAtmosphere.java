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
 * Source Class: DefaultAtmosphere <br>
 * @version generated on Mon May 11 16:25:17 BST 2015 by jbjares
 */
public class DefaultAtmosphere extends WrappedIndividualImpl implements Atmosphere {

    public DefaultAtmosphere(CodeGenerationInference inference, IRI iri) {
        super(inference, iri);
    }





    /* ***************************************************
     * Object Property http://codata.jp/OML-Environment.owl#UnitDimension
     */
     
    public Collection<? extends UnitDimension> getUnitDimension() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_UNITDIMENSION,
                                               DefaultUnitDimension.class);
    }

    public boolean hasUnitDimension() {
	   return !getUnitDimension().isEmpty();
    }

    public void addUnitDimension(UnitDimension newUnitDimension) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_UNITDIMENSION,
                                       newUnitDimension);
    }

    public void removeUnitDimension(UnitDimension oldUnitDimension) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_UNITDIMENSION,
                                          oldUnitDimension);
    }


    /* ***************************************************
     * Object Property http://codata.jp/OML-Environment.owl#UnitSystem
     */
     
    public Collection<? extends UnitSystem> getUnitSystem() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_UNITSYSTEM,
                                               DefaultUnitSystem.class);
    }

    public boolean hasUnitSystem() {
	   return !getUnitSystem().isEmpty();
    }

    public void addUnitSystem(UnitSystem newUnitSystem) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_UNITSYSTEM,
                                       newUnitSystem);
    }

    public void removeUnitSystem(UnitSystem oldUnitSystem) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_UNITSYSTEM,
                                          oldUnitSystem);
    }


}
