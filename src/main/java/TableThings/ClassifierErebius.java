package TableThings;

import com.google.common.base.Optional;
import org.jsoup.nodes.Element;
import webreduce.data.TableType;
import webreduce.extraction.mh.TableClassification;
import webreduce.extraction.mh.features.FeaturesP2;
import webreduce.extraction.mh.tools.TableConvert;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.Instance;

import java.io.InputStream;

public class ClassifierErebius extends TableClassifier {
    private static final String MODEL_PATH = "/RandomForest_P2.mdl"; // Путь к файлу модели
    private TableConvert tableConvert; // Объект для конвертации элементов таблицы
    private Classifier classifier2; // Классификатор Weka
    private FeaturesP2 phase2Features; // Характеристики фазы 2
    private Attribute classAttr2; // Атрибут класса для хранения типа таблицы
    // Переменные для хранения значений типов таблиц
    private double layoutVal, relationVal, entityVal, matrixVal, noneVal;

    public ClassifierErebius(){
        try {
            // Загрузка модели классификации из файла
            classifier2 = loadModelFromClasspath(MODEL_PATH);
        } catch (Exception e){
            e.printStackTrace(); // Логирование ошибок при загрузке модели
        }

        phase2Features = new FeaturesP2(); // Инициализация характеристик фазы 2
        tableConvert = new TableConvert(2, 2); // Инициализация объекта для конвертации элементов

        classAttr2 = new Attribute("class", phase2Features.getClassVector()); // Инициализация атрибута класса
        // Инициализация переменных значений типов таблиц
        relationVal = classAttr2.indexOfValue("RELATION");
        entityVal = classAttr2.indexOfValue("ENTITY");
        matrixVal = classAttr2.indexOfValue("MATRIX");
        noneVal = classAttr2.indexOfValue("NONE");
    }

    @Override
    public TableType classify(Element table){
        // Конвертация элемента table и проверка на присутствие
        Optional<Element[][]> convertedTable = tableConvert.toTable(table);
        if(!convertedTable.isPresent())
            return TableType.LAYOUT; // Если элемента нет, возвращаем тип "LAYOUT"

        // Если элемент присутствует, производим классификацию
        return classifyTable(convertedTable.get());
    }

    public TableType classifyTable(Element[][] convertedTable) {
        try {
            // Вычисление характеристик для convertedTable
            Instance currentInst = phase2Features.computeFeatures(convertedTable);
            double cls = classifier2.classifyInstance(currentInst); // Классификация текущего экземпляра

            TableType resultType;
            // Определение типа таблицы на основе классификации
            if (cls == relationVal)
                resultType = TableType.RELATION;
            else if (cls == entityVal)
                resultType = TableType.ENTITY;
            else if (cls == matrixVal)
                resultType = TableType.MATRIX;
            else if (cls == noneVal)
                resultType = TableType.OTHER;
            else {
                // В случае ошибки возвращаем тип "LAYOUT"
                resultType = TableType.LAYOUT;
            }
            return resultType;
        } catch (Exception e) {
            // Логирование ошибок при классификации
            e.printStackTrace();
            return null; // Возвращаем null в случае ошибки
        }
    }

    // Метод для загрузки модели классификации из classpath
    public static Classifier loadModelFromClasspath(String path) throws Exception {
        InputStream stream = TableClassification.class.getResourceAsStream(path); // Получение потока из файла
        // Чтение и возвращение объекта классификатора из потока
        return (Classifier) weka.core.SerializationHelper.read(stream);
    }
}

