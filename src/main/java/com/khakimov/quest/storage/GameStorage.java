package com.khakimov.quest.storage;

import com.khakimov.quest.model.Question;
import com.khakimov.quest.model.actions.AddItemAction;
import com.khakimov.quest.model.actions.ChangeSceneAction;
import com.khakimov.quest.model.actions.GameAction;
import com.khakimov.quest.model.actions.SetFlagAction;

import java.util.*;

public class GameStorage {
    public static final Map<Integer, Question> scenes = new HashMap<>();

    static {
        // Пролог - ID = 0
        scenes.put(0, new Question(0,
            "В лесной глуши, во тьме безлунной ночи\n" +
            "Скитался парень, волновался, спички жег\n" +
            "И тишиной зловещей лес ему пророчил\n" +
            "Его судьбы весьма безрадостный итог\n" +
            "И каждый куст свирепым хищником казался\n" +
            "Скрипели ветки у него над головой\n" +
            "Перед землянкой он внезапно оказался\n" +
            "До боли напрягая взгляд плененный тьмой.\n\n" +
            "Ты уже сутки пытаешься найти выход из леса, в который забежал спасаясь от вооруженных людей. " +
            "Ты до сих пор не можешь поверит в произошедшее - не успели вы, в месте с командой археологов, разбить лагерь, " +
            "как куча, вооруженных до зубов, головорезов появилась из неоткуда и открыли беспорядочную стрельбу. " +
            "В хаосе происходящего тебе удалось ускользнуть в лес. Так и закончилась, не успев начаться, " +
            "экспедиция по поиску мифический артефакт Лок-Нар.",
            new ArrayList<>() // Пустой список действий - только ввод имени
        ));

        // СЦЕНА 1: Землянка в лесной глуши - ID = 1
        List<GameAction> scene1Actions = new ArrayList<>();
        scene1Actions.add(new ChangeSceneAction(11, "Пройти по тропинке на поле.", 2));
        scene1Actions.add(new ChangeSceneAction(12, "Уйти в лес.", 3));
        scene1Actions.add(new SetFlagAction(13, "Взять вилы стоящие у землянки.", "hasPitchfork", 1));
        scene1Actions.add(new ChangeSceneAction(14, "Постучать в дверь землянки.", 4));

        scenes.put(1, new Question(1, 
            "Ты оказался в лесной глуши, перед землянкой.\n" +
            "Слева от землянки ты видишь тропинку, которая выходит из леса прямо на поле.",
            scene1Actions
        ));

        // СЦЕНА 2: Результат выбора "Пройти по тропинке на поле"
        List<GameAction> scene2Actions = new ArrayList<>();
        scene2Actions.add(new ChangeSceneAction(21, "Искать припасы", 6));
        scene2Actions.add(new ChangeSceneAction(22, "Идти к деревне", 5));

        scenes.put(2, new Question(2,
            "Ты находишься на пшеничном поле, с одной стороны которого лес, а с другой деревня. " +
            "Тропинка, которая должна вести к лесной землянке, неожиданно исчезла.",
            scene2Actions
        ));

        // СЦЕНА 3: Результат выбора "Уйти в лес"
        List<GameAction> scene3Actions = new ArrayList<>();
        scene3Actions.add(new ChangeSceneAction(31, "Искать выход", 6));
        scene3Actions.add(new ChangeSceneAction(32, "Искать припасы", 5));

        scenes.put(3, new Question(3,
            "Вокруг тебя одни деревья, куда идти не понятно",
            scene3Actions
        ));

        // СЦЕНА 4: Результат выбора "Постучать в дверь землянки"
        List<GameAction> scene4Actions = new ArrayList<>();
        scene4Actions.add(new ChangeSceneAction(41, "Уйти", 1));

        scenes.put(4, new Question(4,
            "Дверь землянки открывается и из нее показывается дед, заросший волосами: " +
            "«Иди своей дорогой сталкер!» - говорит старик, уходит обратно в землянку и закрывает за собой дверь.",
            scene4Actions
        ));

        // СЦЕНА 5: Деревня
        List<GameAction> scene5Actions = new ArrayList<>();
        scene5Actions.add(new AddItemAction(51, "Купить еду", "еда", 7));
        scene5Actions.add(new ChangeSceneAction(52, "Спросить про артефакт", 8));

        scenes.put(5, new Question(5,
            "Ты приходишь в деревню. На центральной площади стоит рынок.",
            scene5Actions
        ));

        // СЦЕНА 6: Поиск припасов
        scenes.put(6, new Question(6,
            "Ты нашел старый рюкзак с припасами!",
            new ArrayList<>() // Финальная сцена
        ));

        // СЦЕНА 7: После покупки еды
        scenes.put(7, new Question(7,
            "Ты купил еду и восстановил силы.",
            new ArrayList<>()
        ));

        // СЦЕНА 8: Расспросы про артефакт
        scenes.put(8, new Question(8,
            "Старик на рынке рассказывает тебе легенду о Лок-Наре...",
            new ArrayList<>()
        ));
    }
}