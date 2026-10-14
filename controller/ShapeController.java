package controller;

import model.Circle;
import model.Rectangle;
import model.Shape;
import model.ShapeComparators;
import model.ShapeGenerator;
import model.Triangle;
import view.ShapeView;

import java.util.Arrays;

/** Пов'язує модель і представлення: обробляє команди користувача. */
public class ShapeController {
    private static final int SHAPES_COUNT = 12;

    private final ShapeView view;
    private final Shape[] shapes;

    public ShapeController(ShapeView view) {
        this.view = view;
        this.shapes = new ShapeGenerator().generate(SHAPES_COUNT);
    }

    public void run() {
        view.printShapes("Вихідний набір фігур", shapes);

        boolean running = true;
        while (running) {
            view.printMenu();
            int choice = view.readInt("Ваш вибір: ");
            switch (choice) {
                case 1 -> view.printShapes("Набір фігур", shapes);
                case 2 -> view.printDrawAll(shapes);
                case 3 -> view.printTotalArea(totalArea(shapes));
                case 4 -> printAreaByType();
                case 5 -> {
                    Arrays.sort(shapes, ShapeComparators.BY_AREA_ASC);
                    view.printShapes("Відсортовано за зростанням площі", shapes);
                }
                case 6 -> {
                    Arrays.sort(shapes, ShapeComparators.BY_COLOR);
                    view.printShapes("Відсортовано за кольором", shapes);
                }
                case 0 -> running = false;
                default -> view.printMessage("Невідомий пункт меню.");
            }
        }
        view.printMessage("Роботу завершено.");
    }

    private double totalArea(Shape[] items) {
        double sum = 0;
        for (Shape s : items) {
            sum += s.calcArea();
        }
        return sum;
    }

    private void printAreaByType() {
        view.printTypeMenu();
        int type = view.readInt("Ваш вибір: ");

        Class<? extends Shape> typeClass;
        String typeName;
        switch (type) {
            case 1 -> { typeClass = Rectangle.class; typeName = "Прямокутник"; }
            case 2 -> { typeClass = Triangle.class;  typeName = "Трикутник"; }
            case 3 -> { typeClass = Circle.class;    typeName = "Коло"; }
            default -> {
                view.printMessage("Невідомий вид фігури.");
                return;
            }
        }

        double sum = 0;
        int count = 0;
        for (Shape s : shapes) {
            if (typeClass.isInstance(s)) {
                sum += s.calcArea();
                count++;
            }
        }

        if (count == 0) {
            view.printMessage("Фігур виду \"" + typeName + "\" у наборі не знайдено.");
        } else {
            view.printTypeArea(typeName, sum, count);
        }
    }
}
