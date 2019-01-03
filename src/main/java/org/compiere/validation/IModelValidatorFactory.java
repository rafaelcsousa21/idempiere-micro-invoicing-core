package org.compiere.validation;

/** @author hengsin */
public interface IModelValidatorFactory {

  /**
   * @param className
   * @return new modelvalidator intance
   */
  public ModelValidator newModelValidatorInstance(String className);
}
