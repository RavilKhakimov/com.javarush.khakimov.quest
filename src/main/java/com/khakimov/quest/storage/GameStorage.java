package com.khakimov.quest.storage;

import com.khakimov.quest.model.Question;
import com.khakimov.quest.model.actions.*;

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

                        "Ты уже сутки пытаешься найти выход из леса, в который забежал\n" +
                        "спасаясь от вооруженных людей. Ты до сих пор не можешь поверит\n" +
                        "в произошедшее - не успели вы, в месте с командой археологов, разбить лагерь,\n" +
                        "как куча, вооруженных до зубов, головорезов появилась из неоткуда и открыли\n" +
                        "беспорядочную стрельбу. В хаосе происходящего тебе удалось ускользнуть в лес.\n" +
                        "Так и закончилась, не успев начаться, экспедиция по поиску мифический артефакт Лок-Нар.",
                new ArrayList<>() // Пустой список действий - только ввод имени
        ));

        // СЦЕНА 1: Землянка в лесной глуши - ID = 1
        List<GameAction> scene1Actions = new ArrayList<>();
        scene1Actions.add(new ChangeSceneAction(11, "Пройти по тропинке на поле.", 2));
        scene1Actions.add(new ChangeSceneAction(12, "Уйти в лес.", 3));
        scene1Actions.add(new CompositeAction(13, "Взять вилы стоящие у землянки.", 1)
                .addAction(new SetFlagAction(0, "", "hasPitchfork", 1))
                .addAction(new AddItemAction(0, "", "вилы", 1)));
        scene1Actions.add(new ChangeSceneAction(14, "Постучать в дверь землянки.", 4));

        scenes.put(1, new Question(1,
                "Ты оказался в лесной глуши, перед землянкой.\n" +
                        "Слева от землянки ты видишь тропинку, которая выходит из леса прямо на поле.",
                scene1Actions
        ));

        // СЦЕНА 2: Поле - Результат выбора 11 "Пройти по тропинке на поле"
        List<GameAction> scene2Actions = new ArrayList<>();
        scene2Actions.add(new ChangeSceneAction(21, "Искать припасы", 6));
        scene2Actions.add(new ChangeSceneAction(22, "Идти к деревне", 5));

        scenes.put(2, new Question(2,
                "Ты находишься на пшеничном поле, с одной стороны которого лес, а с другой деревня.\n" +
                        "Тропинка, которая должна вести к лесной землянке, неожиданно исчезла.",
                scene2Actions
        ));

        // СЦЕНА 3: Лес - Результат выбора 12 "Уйти в лес"
        List<GameAction> scene3Actions = new ArrayList<>();
        scene3Actions.add(new ChangeSceneAction(31, "Искать выход", 6));
        scene3Actions.add(new ChangeSceneAction(32, "Искать припасы", 5));

        scenes.put(3, new Question(3,
                "Вокруг тебя одни деревья, куда идти не понятно",
                scene3Actions
        ));

        // СЦЕНА 4: Землянка - Результат выбора 14 "Постучать в дверь землянки"
        List<GameAction> scene4Actions = new ArrayList<>();
        scene4Actions.add(new ChangeSceneAction(41, "Уйти", 1));

        scenes.put(4, new Question(4,
                "Дверь землянки открывается и из нее показывается дед, заросший волосами:\n" +
                        "«Иди своей дорогой сталкер!» - говорит старик, уходит обратно в землянку и закрывает за собой дверь.",
                scene4Actions
        ));

        // СЦЕНА 5: Окраина деревни 22 "Идти к деревне" или 32 "Искать припасы"
        List<GameAction> scene5Actions = new ArrayList<>();
        scene5Actions.add(new ChangeSceneAction(51, "Идти на деревенское кладбище", 6));
        scene5Actions.add(new ChangeSceneAction(52, "Идти в таверну", 7));
        scene5Actions.add(new ChangeSceneAction(53, "Идти в дом, где горит свет", 8));

        scenes.put(5, new Question(5,
                "Ты находишься на окраине небольшой деревни, старые покосившиеся дома не вызывают доверия,\n" +
                        "еще больше не вызывает доверия то, что тут никого нет, ни людей ни домашних животных.",
                scene5Actions
        ));

        // СЦЕНА 6: Деревенское кладбище Результат выбора 51 "Идти на деревенское кладбище"
        List<GameAction> scene6Actions = new ArrayList<>();
        scene6Actions.add(new ChangeSceneAction(61, "Подойти к склепу", 9));
        scene6Actions.add(new ChangeSceneAction(62, "Прислушаться к голосу", 10));
        scene6Actions.add(new ChangeSceneAction(63, "Попытаться разрыть могилу лопатой", 99));
        scene6Actions.add(new ChangeSceneAction(64, "Быстро уйти с кладбища", 99));

        scenes.put(6, new Question(6,
                "Ты выходишь на деревенское кладбище. Воздух холодный и неподвижный,\n" +
                        "пахнет влажной землей и тленом. Кривые надгробия, похожие на пьяных стражников,\n" +
                        "покосились в разные стороны. Посреди всего этого беспорядка высится небольшой каменный\n" +
                        "склеп с заржавевшей дверью.\n\n" +
                        "Ты замечаешь, что одна из свежих могил выглядит неестественно.\n" +
                        "Земля на ней шевелится, будто кто-то пытается выбраться изнутри.\n" +
                        "Оттуда доносится приглушенный,хриплый голос, наполненный обидой и злобой:\n" +
                        "Голос из-под земли: «...И ржали... и водкой поливали... \n" +
                        "В РОЖУ ЦЕЛОВАЛИ! Не забуду... не прощу! Всех вас за собой утащу!»",
                scene6Actions
        ));


        // СЦЕНА 9: Склеп Результат выбора 61 "Подойти к склепу"
        List<GameAction> scene9Actions = new ArrayList<>();
        scene9Actions.add(new ChangeSceneAction(91, "Поговорить с зомби", 99));
        scene9Actions.add(new ChangeSceneAction(92, "Обыскать склеп", 99));
        scene9Actions.add(new ChangeSceneAction(93, "Атаковать зомби", 99));

        scenes.put(9, new Question(9,
                "Ты заходишь в склеп. Внутри царит беспорядок: пустые бутылки, окурки, пустая гильза от патрона.\n" +
                "На каменном саркофаге мелом нарисована насмешливая рожица. В углу ты замечаешь полуразложившегося зомби\n" +
                "в рваной одежде. Он не агрессивен, а просто сидит и тупо смотрит на стену, что-то бессвязно бормоча.",
                scene9Actions
        ));

        // СЦЕНА 10: Склеп Результат выбора 62 "Прислушаться к голосу"
        List<GameAction> scene10Actions = new ArrayList<>();
        scene1Actions.add(new CompositeAction(101, "Отойти от могилы", 6)
                .addAction(new SetFlagAction(0, "", "hasVodkaTask", 1)));

        scenes.put(10, new Question(10,
                "Голос из-под земли (яснее):\n" +
                        "«Они...они надо мной смеялись! Своими пьяными рожами надо мной склонились!\n" +
                        "Оставили меня тут одного, а сами... а сами пошли в тот проклятый склеп\n" +
                        "свою водку допивать! Мой последний запас! Верни мне мою бутыль! Принеси\n" +
                        "ее сюда и разбей о мой камень... maybe тогда я усну... maybe...»",
                scene10Actions
        ));

        // СЦЕНА 99: Смерть
        List<GameAction> scene99Actions = new ArrayList<>();
        scene99Actions.add(new RestartGameAction(991, "Начать новую игру", 0));

        scenes.put(99, new Question(99,
                "ПОТРАЧЕНО",
                scene99Actions
        ));
    }
}