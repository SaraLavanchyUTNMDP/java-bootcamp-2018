-- phpMyAdmin SQL Dump
-- version 4.8.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 14-05-2018 a las 15:27:04
-- Versión del servidor: 10.1.31-MariaDB
-- Versión de PHP: 7.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `highschool`
--

DELIMITER $$
--
-- Procedimientos
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `addCourse` (IN `vteacher` VARCHAR(50), IN `vname` VARCHAR(50), IN `hours` INT)  BEGIN
    insert into courses(hours_week, name) values (hours, vname);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `addStudent` (IN `vname` VARCHAR(50), IN `vsurname` VARCHAR(50), IN `birth` DATE)  BEGIN
    insert into students (name, surname, birth_date) values (vname, vsurname, birth);
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `addTeacher` (`vname` VARCHAR(50), `vsurname` VARCHAR(50), `birth` DATE)  BEGIN
	insert into teachers (birth_date, name, surname) values (birth, vname, vsurname);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `approved_students` (IN `vcourse` VARCHAR(30))  BEGIN
			select (a.approved / a.students) as 'approved' from (
    	select approved_students.approved as 'approved', count(ss.id_student) 'students', ss.id_course as 'id_course' from
        (select count(final_note) as 'approved', ss.id_course as 'course'  
        from student_per_schedule ss
        INNER join courses c
        on c.id_course=ss.id_course
        where c.name=vcourse and ss.final_note>3
        group by ss.id_course
    	) approved_students
        inner join student_per_schedule ss
        on approved_students.course = ss.id_course
        group by ss.id_course ) a        
        group by a.id_course;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `final_note` (IN `vcourse` VARCHAR(30))  BEGIN
declare finalnote int DEFAULT 0;
declare idstudent_schedule int;
declare vfin boolean DEFAULT false;

declare mycur cursor for select id_student_per_schedule from student_per_schedule ss 
							inner join courses c
                            on c.id_course=ss.id_course
                           	inner join schedule_times st
                            on st.id_course=ss.id_course
                        	where st.schedule_year = year(CURRENT_DATE) 
                            and c.name=vcourse;

declare continue handler for not found set vfin = true;

open mycur;
	myloop : LOOP
    
    fetch mycur into idstudent_schedule;
    
    if vfin THEN
    	leave myloop;
    end if;
    
    start TRANSACTION;
    	set finalnote = (select avg(note) from partials p where p.id_student_per_schedule=idstudent_schedule);
        update student_per_schedule set final_note=finalnote where id_student_per_schedule=idstudent_schedule;
        
        if (finalnote = 0) then 
        	signal SQLSTATE '45000' SET MESSAGE_TEXT = 'the final note couldnt be calculated, try later'; 
        end if;
    commit;
    end loop;
close mycur;
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getcourse` (`cname` VARCHAR(30))  BEGIN
	select * from courses where name LIKE cname limit 1;
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `newschedule` (IN `vcname` VARCHAR(30), IN `vday` VARCHAR(30), IN `syear` YEAR, IN `tbegin` TIME, IN `tend` TIME, IN `vteacher` VARCHAR(30))  BEGIN
	declare idcourse int;
    declare idteacher int;
    set idcourse = (select id_course from courses c where c.name = vcname limit 1);
    set idteacher = (select id_teacher from teachers t where t.name = vteacher);
	insert into schedule_times(id_teacher, day, id_course, schedule_year, time_begin, time_end) values (vidteacher, day, idcourse, syear, tbegin, tend);
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `schedule_teachers` (`vteacher` VARCHAR(30))  BEGIN
	select t.surname, t.name, st.day_begin, concat(st.time_begin,' - ',st.time_end) as 'schedule', c.name 
    from teachers t
    inner join schedule_times st
    on st.id_teacher=t.id_teacher
    INNER join courses c
    on c.id_course=st.id_course
    where t.name=vteacher;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `students_courses` (IN `vname` VARCHAR(50), IN `vsurname` VARCHAR(50), IN `birth` DATE, IN `vcourse` VARCHAR(30))  BEGIN
	declare idstudent int;
    declare idcourse int;
    
    call addStudent(vname, vsurname, birth);
    set idstudent = LAST_INSERT_ID();
    set idcourse = (select id_course from courses c where c.name like vcourse limit 1);
    insert into student_per_schedule(id_course, id_student) values (idcourse, idstudent);
    
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `courses`
--

CREATE TABLE `courses` (
  `id_course` int(11) NOT NULL,
  `hours_week` int(11) NOT NULL,
  `name` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `courses`
--

INSERT INTO `courses` (`id_course`, `hours_week`, `name`) VALUES
(1, 20, 'tsp'),
(2, 25, 'ingeniero en sistemas'),
(3, 15, 'martillero publico');

-- --------------------------------------------------------

--
-- Estructura Stand-in para la vista `courses_and_students`
-- (Véase abajo para la vista actual)
--
CREATE TABLE `courses_and_students` (
);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `partials`
--

CREATE TABLE `partials` (
  `id_partial` int(11) NOT NULL,
  `number` int(11) DEFAULT NULL,
  `id_student_per_schedule` int(11) NOT NULL,
  `note` enum('1','2','3','4','5','6','7','8','9','10') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `partials`
--

INSERT INTO `partials` (`id_partial`, `number`, `id_student_per_schedule`, `note`) VALUES
(1, 1, 2, '8'),
(2, 1, 3, '2'),
(3, 1, 4, '9'),
(4, 1, 5, '5'),
(5, 1, 6, '8'),
(6, 1, 7, '3'),
(7, 1, 8, '4'),
(8, 1, 9, '7'),
(9, 1, 10, '3'),
(10, 1, 11, '3'),
(11, 2, 2, '5'),
(12, 2, 3, '4'),
(13, 2, 4, '7'),
(14, 2, 5, '3'),
(15, 2, 6, '8'),
(17, 2, 7, '3'),
(18, 2, 8, '9'),
(19, 2, 9, '3'),
(20, 2, 10, '8'),
(21, 2, 11, '3'),
(22, 3, 2, '3'),
(23, 3, 3, '5'),
(24, 3, 4, '8'),
(25, 3, 5, '3'),
(26, 3, 6, '8'),
(27, 3, 7, '2'),
(28, 3, 8, '4'),
(29, 3, 9, '10'),
(30, 3, 10, '1'),
(31, 3, 11, '3');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `schedule_times`
--

CREATE TABLE `schedule_times` (
  `id_schedule` int(11) NOT NULL,
  `id_course` int(11) NOT NULL,
  `id_teacher` int(11) DEFAULT NULL,
  `schedule_year` year(4) DEFAULT NULL,
  `day_begin` enum('monday','tuesday','wednesday','thursday','friday','saturday') DEFAULT NULL,
  `time_begin` time DEFAULT NULL,
  `time_end` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `schedule_times`
--

INSERT INTO `schedule_times` (`id_schedule`, `id_course`, `id_teacher`, `schedule_year`, `day_begin`, `time_begin`, `time_end`) VALUES
(9, 1, 1, 2018, 'monday', '18:00:00', '20:00:00'),
(10, 1, 1, 2018, 'thursday', '20:30:00', '22:30:00'),
(11, 1, 2, 2018, 'monday', '20:00:00', '22:00:00'),
(12, 1, 2, 2018, 'thursday', '18:00:00', '20:00:00'),
(13, 1, 3, 2018, 'tuesday', '18:00:00', '20:00:00'),
(14, 1, 3, 2018, 'tuesday', '20:30:00', '22:30:00'),
(15, 1, 2, 2018, 'friday', '18:20:00', '20:20:00'),
(16, 1, 2, 2018, 'friday', '21:00:00', '23:00:00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `students`
--

CREATE TABLE `students` (
  `registration` int(11) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `surname` varchar(50) DEFAULT NULL,
  `birth_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `students`
--

INSERT INTO `students` (`registration`, `name`, `surname`, `birth_date`) VALUES
(15, 'shimi', 'soria', '2012-01-02'),
(16, 'joan', 'stevez', '1994-04-15'),
(20, 'tamago', 'shaki', '1994-05-15'),
(21, 'nimrot', 'stuberrth', '2000-11-09'),
(22, 'gilgamesh', 'babilonus', '1992-11-22'),
(23, 'gilart', 'shoubert', '2001-10-14'),
(24, 'david', 'lavanchy', '1969-05-18'),
(25, 'noemi', 'tejada', '1998-04-23'),
(26, 'esteban', 'rodriguez', '1970-11-18'),
(27, 'lucas', 'giovini', '2001-05-21');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `student_per_schedule`
--

CREATE TABLE `student_per_schedule` (
  `id_student_per_schedule` int(11) NOT NULL,
  `id_course` int(11) NOT NULL,
  `id_student` int(11) NOT NULL,
  `final_note` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `student_per_schedule`
--

INSERT INTO `student_per_schedule` (`id_student_per_schedule`, `id_course`, `id_student`, `final_note`) VALUES
(2, 1, 20, 5),
(3, 1, 21, 4),
(4, 1, 22, 8),
(5, 1, 23, 4),
(6, 1, 16, 8),
(7, 1, 15, 3),
(8, 1, 24, 6),
(9, 1, 25, 7),
(10, 1, 26, 4),
(11, 1, 27, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `teachers`
--

CREATE TABLE `teachers` (
  `id_teacher` int(11) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `surname` varchar(50) DEFAULT NULL,
  `birth_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `teachers`
--

INSERT INTO `teachers` (`id_teacher`, `name`, `surname`, `birth_date`) VALUES
(1, 'rodrigo', 'soria', '1998-07-03'),
(2, 'juan', 'espinoza', '1994-08-15'),
(3, 'santiago', 'marquez', '1950-11-16');

-- --------------------------------------------------------

--
-- Estructura para la vista `courses_and_students`
--
DROP TABLE IF EXISTS `courses_and_students`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `courses_and_students`  AS  select `c`.`name` AS `course`,`t`.`name` AS `teacher name`,`t`.`surname` AS `teacher surname`,`s`.`surname` AS `student surname`,`s`.`name` AS `student name` from (((`courses` `c` join `teachers` `t` on((`c`.`id_teacher` = `t`.`id_teacher`))) join `student_per_schedule` `ss` on((`c`.`id_course` = `ss`.`id_course`))) join `students` `s` on((`ss`.`id_student` = `s`.`registration`))) order by `c`.`name`,`s`.`surname` ;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `courses`
--
ALTER TABLE `courses`
  ADD PRIMARY KEY (`id_course`);

--
-- Indices de la tabla `partials`
--
ALTER TABLE `partials`
  ADD PRIMARY KEY (`id_partial`),
  ADD KEY `id_student_per_schedule` (`id_student_per_schedule`);

--
-- Indices de la tabla `schedule_times`
--
ALTER TABLE `schedule_times`
  ADD PRIMARY KEY (`id_schedule`),
  ADD KEY `id_course` (`id_course`),
  ADD KEY `id_teacher` (`id_teacher`);

--
-- Indices de la tabla `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`registration`);

--
-- Indices de la tabla `student_per_schedule`
--
ALTER TABLE `student_per_schedule`
  ADD PRIMARY KEY (`id_student_per_schedule`),
  ADD KEY `id_student` (`id_student`),
  ADD KEY `student_per_schedule_ibfk_1` (`id_course`);

--
-- Indices de la tabla `teachers`
--
ALTER TABLE `teachers`
  ADD PRIMARY KEY (`id_teacher`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `courses`
--
ALTER TABLE `courses`
  MODIFY `id_course` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `partials`
--
ALTER TABLE `partials`
  MODIFY `id_partial` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT de la tabla `schedule_times`
--
ALTER TABLE `schedule_times`
  MODIFY `id_schedule` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT de la tabla `students`
--
ALTER TABLE `students`
  MODIFY `registration` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT de la tabla `student_per_schedule`
--
ALTER TABLE `student_per_schedule`
  MODIFY `id_student_per_schedule` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `teachers`
--
ALTER TABLE `teachers`
  MODIFY `id_teacher` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `partials`
--
ALTER TABLE `partials`
  ADD CONSTRAINT `partials_ibfk_1` FOREIGN KEY (`id_student_per_schedule`) REFERENCES `student_per_schedule` (`id_student_per_schedule`);

--
-- Filtros para la tabla `schedule_times`
--
ALTER TABLE `schedule_times`
  ADD CONSTRAINT `schedule_times_ibfk_1` FOREIGN KEY (`id_course`) REFERENCES `courses` (`id_course`),
  ADD CONSTRAINT `schedule_times_ibfk_2` FOREIGN KEY (`id_teacher`) REFERENCES `teachers` (`id_teacher`);

--
-- Filtros para la tabla `student_per_schedule`
--
ALTER TABLE `student_per_schedule`
  ADD CONSTRAINT `student_per_schedule_ibfk_1` FOREIGN KEY (`id_course`) REFERENCES `courses` (`id_course`),
  ADD CONSTRAINT `student_per_schedule_ibfk_2` FOREIGN KEY (`id_student`) REFERENCES `students` (`registration`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
