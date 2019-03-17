package org.compiere.validation;

/**
 * @author hengsin
 */
public interface IModelValidatorFactory {

    /**
     * @param className
     * @return new modelvalidator intance
     */
    ModelValidator newModelValidatorInstance(String className);
}
