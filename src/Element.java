/**
 * Класс Элемент со свойствами <b>type</b> и <b>value</b>.
 * @autor Никита Макаров
 * @version 1.3
 */
public class Element {
    /** Поле типа */
    private final TypeExpressions type;
    /** Поле значения только для типа NUMBER, в других типах по умолчанию 0*/
    private double value;

    /**
     * Метод получения значения поля {@link Element#type}
     * @return возвращает тип элемента
     */
    public TypeExpressions getType(){
        return this.type;
    }

    /**
     * Метод получения значения поля {@link Element#value}
     * @return возвращает значение элемента (NUMBER)
     */
    public double getValue() {
        return this.value;
    }
    /**
     * Конструктор копирвоания
     * @param elem - объект, который копируем
     * @see Element#Element(TypeExpressions, double)
     * @see Element#Element(TypeExpressions)
     */
    Element (Element elem) {
        this.type = elem.type;
        this.value = elem.value;
    }
    /**
     * Конструктор
     * @param type - тип элемента (для данного конструктора NUMBER)
     * @param value - значение элемента
     * @see Element#Element(Element)
     * @see Element#Element(TypeExpressions)
     */
    Element (TypeExpressions type, double value) {
        this.type = type;
        this.value = value;
    }
    /**
     * Конструктор
     * @param type - тип элемента
     * @see Element#Element(Element)
     * @see Element#Element(TypeExpressions, double)
     */
    Element (TypeExpressions type) {
        this.type = type;
    }
}
