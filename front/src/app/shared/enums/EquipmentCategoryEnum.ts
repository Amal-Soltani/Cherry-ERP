export class EquipmentCategoryEnum {

  static readonly MACHINE = new EquipmentCategoryEnum('MACHINE', $localize `Machine`, '#2696fc');
  static readonly CAR = new EquipmentCategoryEnum('CAR', $localize `Véhicule`, '#98fc26');
  static readonly IMMOVABLE = new EquipmentCategoryEnum('IMMOVABLE', $localize `Immeuble`, '#fc9c26');
  static readonly OFFICE_SUPPLIES = new EquipmentCategoryEnum('OFFICE_SUPPLIES', $localize `Fournitures de bureau`, '#c326fc');
  static readonly FURNITURE = new EquipmentCategoryEnum('FURNITURE', $localize `Meuble`, '#fc26a6');

  private constructor(
    /**
     * The name of the instance; should be exactly the variable name,
     * for serializing/deserializing simplicity.
     */
    public readonly name: string,
    public readonly label: string,
    public readonly color: string) {

    this.name = name;
    this.label = label;
    this.color = color;
  }

  static get values(): EquipmentCategoryEnum[] {
    return [
      this.MACHINE,
      this.CAR,
      this.IMMOVABLE,
      this.OFFICE_SUPPLIES,
      this.FURNITURE
    ];
  }

  static getListLabes(): EquipmentCategoryEnum[] {
    return [
      this.MACHINE,
      this.CAR,
      this.IMMOVABLE,
      this.OFFICE_SUPPLIES,
      this.FURNITURE
    ];
  }

  /**
   * Converts a string to its corresponding Status instance.
   *
   * @param string the string to convert to Status
   * @throws RangeError, if a string that has no corresponding Status value was passed.
   * @returns the matching Status
   */
  static fromString(str: string): EquipmentCategoryEnum {
    // Works assuming the name property of the status is identical to the variable's name.
    // Alternatively, you can search the .values array
    const value = (this as any)[str];
    if (value) {
      return value;
    }

    throw new RangeError(`Illegal argument passed to fromString(): $ {string} does not correspond to any instance of the enum ${(this as any).prototype.constructor.name}`
    );
  }

  /**
   * Called when converting the Enum value to a string using JSON.Stringify.
   * Compare to the fromString() method, which deserializes the object.
   */
  public getName(): string {
    return this.name;
  }

  public getLabel(): string {
    return this.label;
  }
}

