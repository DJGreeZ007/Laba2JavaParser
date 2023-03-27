import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;
/**
 * Основной класс парсера.
 * Возможно добавления свойства error, для информирования пользователя, после нахождения результата.
 * @autor Макаров Никита
 * @version 3.0
 */
public class MathParser {
    /**
     * Метод получения названий всех переменных в строке
     * @param expression - строка, содержащее выражение для высчитывания
     * @return возвращает массив из названий переменных
     */

    private String[] getVariableNames(String expression) {
        // Текущий символ относится к переменной
        boolean beginningVariableName = false;
        // Хранение название переменных
        HashSet<String> variablesHash = new HashSet<>();
        // Хранение букв переменной
        StringBuilder variable = new StringBuilder();
        // Проход по всему выражению
        for (int i = 0; i < expression.length(); ++i) {
            // Запоминаем текущий символ
            char letter = expression.charAt(i);
            // Если этот символ относится к букве
            if (letter >= 'a' && letter <= 'z' ||
                    letter >= 'A' && letter <= 'Z') {
                // Добавляем букву в название переменной
                variable.append(letter);
                // Выставляем флаг, что текущий символ - название переменной
                beginningVariableName = true;
            }
            // Если встретилась не буква
            else {
                // был ли предыдущий символ буквой
                if (beginningVariableName) {
                    // записываем название переменной в общий список
                    variablesHash.add(variable.toString());
                    // сбрасываем флаг
                    beginningVariableName = false;
                    // сбрасываем название переменной
                    variable = new StringBuilder();
                }
            }
        }
        // если название переменной было последним в выражении
        if (beginningVariableName) {
            // записываем переменную
            variablesHash.add(variable.toString());
        }
        // возвращаем список переменных в виде String[]
        return variablesHash.toArray(new String[0]);
    }
    /**
     * Метод получения от пользователя всех значений переменных в выражении
     * @param variablesNames - список переменных {@link MathParser#getVariableNames(String)}
     * @return возвращает список элементов с type = NUMBER и значениями от пользователя
     */
    private ArrayList<Element> getVariableValuesFromKeyboard (String[] variablesNames) {
        //Список элементов с type = NUMBER
        ArrayList<Element> variables = new ArrayList<>();
        //Объект типа Scanner
        Scanner in = new Scanner(System.in);
        //Основной проход по названиям переменных
        for (int i = 0 ; i < variablesNames.length; ++i) {
            //фалг ошибки
            boolean err = false;
            //значение, в которое попадёт значение введенное пользователем, в случае ошибки = 0
            double num = 0;
            //Запрос на ввод
            System.out.print("Введите значение переменной " + variablesNames[i] + ": " );
            String number = in.nextLine();
            try {
                //Попытка считать значение пользователя
                num = Double.parseDouble(number);
            }
            catch (Exception e) {
                //Вывод ошибки при некорректном вводе
                System.out.println("Exception: " + e);
                //поднятие флага ошибки
                err = true;
            }
            //Если произошла ошибка, откатываем счётчик, чтобы опять запросить число от пользователя
            if (err) {
                i--;
            }
            //иначе записываем элемент в список со значением от пользователя
            else {
                variables.add(new Element(TypeExpressions.NUMBER, num));
            }
        }
        //Закрываем поток
        in.close();
        //Возврат значения
        return variables;
    }

    /**
     * Метод перевода выражения в список элементов
     * Использует методы
     * {@link MathParser#getVariableValuesFromKeyboard(String[])} и
     * {@link MathParser#getVariableNames(String)}
     * @param expression - выражение введенное пользователем
     * @return возвращает переведенное пользовательское выражение в виде списка элементов
     */
    private ArrayList<Element> expressionToList (String expression) {
        //Список всех используемых переменных
        String[] variablesNames = getVariableNames(expression);
        //Заполнение переменных с клавиатуры
        ArrayList<Element> variables = getVariableValuesFromKeyboard(variablesNames);
        //Результирующий массив элементов
        ArrayList<Element> result = new ArrayList<>();
        //Запоминание числа
        String number = "";
        //Запоминание названия переменной
        String variable = "";
        //Основной проход по выражению
        for (int i = 0 ; i < expression.length(); ++i) {
            //Запоминаем текущий символ строки
            char sym = expression.charAt(i);
            switch (sym) {
                //Если встретились символы: ()+-/* - запоминаем их
                case '(' -> result.add(new Element(TypeExpressions.LEFT_BRACKET));
                case ')' -> result.add(new Element(TypeExpressions.RIGHT_BRACKET));
                case '+' -> result.add(new Element(TypeExpressions.PLUS));
                case '-' -> result.add(new Element(TypeExpressions.MINUS));
                case '/' -> result.add(new Element(TypeExpressions.DIV));
                case '*' -> result.add(new Element(TypeExpressions.MUL));
                default -> {
                    //Если встретились символы числа
                    if (sym >= '0' && sym <= '9' || sym == '.') {
                        //Добавление символа к number
                        number += sym;
                        //Флаг на возвожность добавления в общий список
                        boolean canAdd = false;
                        //Если последний символ относится к числу
                        if (i >= expression.length() - 1) {
                            //Флаг - можно добавлять
                            canAdd = true;
                        } else {
                            //берем следующий символ
                            sym = expression.charAt(i + 1);
                            //если следующий символ не относится к символам числа
                            if (!(sym >= '0' && sym <= '9' || sym == '.')) {
                                //Флаг - можно добавлять
                                canAdd = true;
                            }
                        }
                        //Можно добавлять
                        if (canAdd) {
                            double num = 0;
                            try {
                                //Попытка привести текущее число к типу double
                                num = Double.parseDouble(number);
                            } catch (Exception e) {
                                //Вывод ошибки
                                System.out.println("Exception: " + e + " replaced by 0");
                            }
                            //В результирующий список добавляется Element с типом = number и значением если нет ошибки num, иначе 0
                            result.add(new Element(TypeExpressions.NUMBER, num));
                            //Сбрасываем число
                            number = "";
                        }
                    }
                    //Если встретился символ переменной
                    else if (sym >= 'a' && sym <= 'z' || sym >= 'A' && sym <= 'Z') {
                        //Добавляем символ названия переменной в переменную
                        variable += sym;
                        //Флаг - возможность добавления
                        boolean canAdd = false;
                        //
                        //Если последний символ относится к переменной
                        if (i >= expression.length() - 1) {
                            //Флаг - можно добавлять
                            canAdd = true;
                        } else {
                            //считываем слудующий символ
                            sym = expression.charAt(i + 1);
                            //Если следующий символ не относится к переменной
                            if (!(sym >= 'a' && sym <= 'z' || sym >= 'A' && sym <= 'Z')) {
                                //Флаг - можно добавлять
                                canAdd = true;
                            }
                        }
                        //Можно добавлять
                        if (canAdd) {
                            //Проход по названиям переменных
                            for (int j = 0; j < variables.size(); ++j) {
                                //Если название текузей переменной совпало с названием из списка переменных
                                if (Objects.equals(variablesNames[j], variable)) {
                                    //Добавляем в результирующий список
                                    result.add(variables.get(j));
                                }
                            }
                            //Сбрасываем название переменной
                            variable = "";
                        }
                    }
                    else {
                        System.out.println("Error");
                    }
                }
            }
        }
        //Выводим результат (Список элементов)
        return result;
    }

    /**
     * Рекурсивный метод нахождения итогового значения
     * @param elements - выражение введенное пользователем в виде списка Элементов
     * @return возвращает результат выражения
     */
    private Element calculation (ArrayList<Element> elements) {
        //Объект результата
        Element elem = new Element(TypeExpressions.NUMBER, 0);
        //Переменная баланса скобок
        int balanceBrackets = 0;
        //Список элементов содержащийся внутри скобок
        ArrayList<Element> inside = new ArrayList<>();
        //Цикл поиска скобок
        for(int i = 0; i < elements.size(); ++i) {
            //Если встретилась открывающаяся скобка
            if(elements.get(i).getType() == TypeExpressions.LEFT_BRACKET) {
                balanceBrackets++;
            }
            //Если встретилась закрывающаяся скобка
            if(elements.get(i).getType() == TypeExpressions.RIGHT_BRACKET) {
                balanceBrackets--;
                //Если встретилась закрывающая скобка самой первой найденой скобки
                if(balanceBrackets == 0) {
                    //Добавляем саму закрывающую скобку
                    inside.add(new Element(elements.get(i)));
                    //Удаляем закрывающую скобку
                    elements.remove(i);
                    //Смещаем индекс
                    --i;
                    //Удаляем левую и правую скобку
                    inside.remove(inside.size()-1);
                    inside.remove(0);
                    //Добавляем элемент на место удаленных путем рекурсивного высчитывания
                    elements.add(i+1, calculation(inside));
                    //Очищаем внутренность скобок
                    inside.clear();
                }
            }
            //Если текущий элемент находится внутри скобок
            if (balanceBrackets > 0) {
                //добавление элемента во внутрянку
                inside.add(new Element(elements.get(i)));
                //удаление элемента из общего списка
                elements.remove(i);
                //смещение индекса
                --i;
            }
        }
        //Если количество открывающихся и закрывающихся скобок не равно
        if(balanceBrackets != 0) {
            System.out.println("error: ()");
            //error
        }
        else {
            //Цикл замены умножения и деления в выражении
            for (int i = 0; i < elements.size(); ++i) {
                //Если текущий элемент знак умножения или деления
                if (elements.get(i).getType() == TypeExpressions.MUL || elements.get(i).getType() == TypeExpressions.DIV) {
                    //Если умножение стоит в начале или в конце
                    if (i <= 0 || i >= elements.size() - 1) {
                        System.out.println("error");
                        //error
                    } else {
                        //Проверка, что слева и справа стоят числа
                        if (elements.get(i - 1).getType() != TypeExpressions.NUMBER ||
                                elements.get(i + 1).getType() != TypeExpressions.NUMBER) {
                            System.out.println("error");
                            //error
                        }
                        //запоминаем знак через флаг
                        boolean mulOrDiv = elements.get(i).getType() == TypeExpressions.DIV; // false mul; true div
                        //запоминаем числа (левое и правое)
                        double dividendOrMultiplier = elements.get(i - 1).getValue();
                        double divisorOrMultiplier = elements.get(i + 1).getValue();
                        //Удаляем знак и число из выражения
                        elements.remove(i);
                        elements.remove(i);
                        //смещаем индекс
                        i--;
                        double res = 0;
                        //проверка знака
                        if (mulOrDiv) {
                            try {
                                //попытка деления
                                res = dividendOrMultiplier / divisorOrMultiplier;
                            } catch (Exception e) {
                                //error
                                System.out.println("error: " + e);
                            }
                        } else {
                            //множение
                            res = dividendOrMultiplier * divisorOrMultiplier;
                        }
                        //вставка значения умножения/деления
                        elements.set(i, new Element(TypeExpressions.NUMBER, res));
                    }
                }
            }
            //минус в начале выражения (-число +-/* ...)
            if (elements.get(0).getType() == TypeExpressions.MINUS) {
                //Убираем - в начале выражения
                elements.set(1, new Element(elements.get(1).getType(), -elements.get(1).getValue()));
                elements.remove(0);
            }
            //цикл замены знака плюс и минус на из результаты
            for (int i = 0; i < elements.size(); ++i) {
                //Если встретился знак +/-
                if (elements.get(i).getType() == TypeExpressions.PLUS || elements.get(i).getType() == TypeExpressions.MINUS) {
                    //Если знак минус и плюс стоят в начале или конце выражения
                    if (i <= 0 || i >= elements.size() - 1) {
                        System.out.println("error");
                        //error
                    } else {
                        //Если слева и справа не числа
                        if (elements.get(i - 1).getType() != TypeExpressions.NUMBER ||
                                elements.get(i + 1).getType() != TypeExpressions.NUMBER) {
                            System.out.println("error");
                            //error
                        }
                        //запоминаем знак в виде флага
                        boolean plusOrMinus = elements.get(i).getType() == TypeExpressions.MINUS; // false plus; true minus
                        //Запоминаем левое и правое число
                        double firstNum = elements.get(i - 1).getValue();
                        double secondNum = elements.get(i + 1).getValue();
                        //Удаляем знак и число из выражения
                        elements.remove(i);
                        elements.remove(i);
                        //Смещаем индекс
                        i--;
                        //Результат суммы или разности
                        double res;
                        //Смотри знак
                        if (plusOrMinus) {
                            res = firstNum - secondNum;
                        } else {
                            res = firstNum + secondNum;
                        }
                        //Записываем результат в выражние
                        elements.set(i, new Element(TypeExpressions.NUMBER, res));
                    }
                }
            }
            //На этом этапе в выражении должно остаться только один элемент с type = NUMBER
            if (elements.size() == 1) {
                elem = elements.get(0);
            } else {
                System.out.println("error");
                //error
            }
        }
        //возвращаем результат высчитывания
        return elem;
    }

    /**
     * Основной метод высчитывания выражения
     * @param expression - выражение введенное пользователем
     * @return возвращает результат выражения
     */
    public double mathParser (String expression) {
        //Перевод в список элементов
        ArrayList<Element> elements = expressionToList(expression);
        //Рекурсивный метод высчитывания значения
        Element elem = calculation(elements);
        //Вывод результата
        return elem.getValue();
    }


}


