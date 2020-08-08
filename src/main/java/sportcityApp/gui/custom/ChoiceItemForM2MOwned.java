package sportcityApp.gui.custom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sportcityApp.gui.controllers.EntityInputFormController;

@AllArgsConstructor
/*E - это владелец отношение(спортсмен), T - это замапленная сторона(тренера)*/
public class ChoiceItemForM2MOwned<E, T> {

    public interface EntityFieldSetter<T>{
        void setField(T value);
    }

    public interface EntityFieldPreviousRemover<T>{
        void removeField(T value);
    }

    @Getter
    private final E value;
    private final String stringValue;

    public EntityFieldSetter<T> fieldSetter;
    public EntityFieldPreviousRemover<T> fieldRemover;

    /*
    public EntityFieldSetter<E> fieldSetterFrontend;
    public EntityInputFormController.EntityFieldPreviousRemover<E> fieldRemoverFrontend;
    */

    @Override
    public String toString() {
        return stringValue;
    }

}
