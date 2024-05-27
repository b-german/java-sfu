package sfu.student.pr6;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Стэк, в котором можно хранить объекты произвольного типа, определенного параметром типа
 *
 * <p>Стэк поддерживает следующие операции:
 * <ul>
 *   <li> проверить, стек пуст/не пуст</li>
 *   <li> втолкнуть элемент</li>
 *   <li> вытолкнуть элемент</li>
 *   <li> получить вершину стека</li>
 *   <li> обменять значения двух верхних элементов стека</li>
 *   <li> извлечь из стека первое вхождение передаваемого значения</li>
 *   <li> вывод стека на экран</li>
 * <ul/>
 *
 * @param <T> тип объектов, которые будут храниться в стеке
 */
public class ArrayStack<T> {

  private static final int DEFAULT_CAPACITY = 10;
  private static final String STACK_IS_EMPTY_ERROR = "Стэк пуст!";
  private static final String NULL_ARGUMENT_ERROR = "Передан пустой элемент!";
  private static final String STACK_SIZE_ERROR = "Размер стека не может быть меньше 1!";
  private static final String STACK_SWAP_ERROR = "В стэке нет двух объектов!";
  private T[] stack;
  private int size;

  /**
   * Конструктор стека с размером по умолчанию {@link #DEFAULT_CAPACITY}
   */
  @SuppressWarnings("unchecked")
  public ArrayStack() {
    this.stack = (T[]) new Object[DEFAULT_CAPACITY];
    this.size = 0;
  }

  /**
   * Конструктор стека с указанием размера
   *
   * @param capacity - размер стека
   * @throws IllegalArgumentException если <code>capacity</code> меньше или равно <code>0</code>
   */
  @SuppressWarnings("unchecked")
  public ArrayStack(int capacity) {
    if (capacity <= 0) {
      throw new IllegalArgumentException(STACK_SIZE_ERROR);
    }
    this.stack = (T[]) new Object[capacity];
    this.size = 0;
  }

  /**
   * Проверка стека на пустоту
   *
   * @return <code>true</code>, если пуст, иначе <code>false<code/>
   */
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * Добавляет элемент на вершину стека
   *
   * @param value элемент типа {@link T} для добавления
   * @throws IllegalArgumentException если передано пустое значение
   */
  public void push(T value) {
    if (Objects.isNull(value)) {
      throw new IllegalArgumentException(NULL_ARGUMENT_ERROR);
    }
    if (size == stack.length) {
      stack = Arrays.copyOf(stack, stack.length * 2);
    }
    stack[size++] = value;
  }

  /**
   * Удаляет и возвращает элемент с вершины стека
   *
   * @return элемент с вершины стека типа {@link T}
   * @throws NoSuchElementException если стек пуст
   */
  public T pop() {
    if (isEmpty()) {
      throw new NoSuchElementException(STACK_IS_EMPTY_ERROR);
    }

    size--;
    T value = stack[size];
    stack[size] = null;
    return value;
  }

  /**
   * Получить вершину стека
   *
   * @return элемент с вершины стека
   * @throws NoSuchElementException если стек пуст
   */
  public T peek() {
    if (isEmpty()) {
      throw new NoSuchElementException(STACK_IS_EMPTY_ERROR);
    }

    return stack[size - 1];
  }

  /**
   * Обменять значения двух верхних элементов стека
   *
   * @throws IllegalStateException если в стеке меньше двух элементов
   */
  public void swap() {
    if (size < 2) {
      throw new IllegalStateException(STACK_SWAP_ERROR);
    }

    T temp = stack[size - 1];
    stack[size - 1] = stack[size - 2];
    stack[size - 2] = temp;
  }

  /**
   * Извлекает первое вхождение передаваемого значения из стека
   *
   * @param value значение для ивзлечения
   * @return <code>true</code>, если значение извлечено, иначе <code>false</code>
   * @throws IllegalArgumentException если передано пустое значение для извлечения
   */
  public boolean remove(T value) {
    int index = findIndex(value);
    if (index < 0) {
      return false;
    }

    for (int i = index; i < size - 1; i++) {
      stack[i] = stack[i + 1];
    }
    stack[--size] = null;
    return true;
  }

  /**
   * Находит индекс первого вхождения передаваемого значения из стека
   *
   * @param value значение для поиска
   * @return <code>-1</code>, если элемент не найден, иначе индекс элемента
   * @throws IllegalArgumentException если передано пустое значение для поиска
   */
  public int findIndex(T value) {
    if (Objects.isNull(value)) {
      throw new IllegalArgumentException(NULL_ARGUMENT_ERROR);
    }

    for (int i = 0; i < size; i++) {
      if (value.equals(stack[i])) {
        return i;
      }
    }

    return -1;
  }

  /**
   * Выводит все элементы стека на экран
   *
   * @throws NoSuchElementException если стек пуст
   */
  public void printStack() {
    if (isEmpty()) {
      throw new NoSuchElementException(STACK_IS_EMPTY_ERROR);
    }

    StringBuilder sb = new StringBuilder();
    sb.append("Стэк:%s".formatted(System.lineSeparator()));
    for (int i = 0; i < size; i++) {
      sb.append("%s%s".formatted(stack[i], System.lineSeparator()));
    }
    System.out.println(sb);
  }


}
