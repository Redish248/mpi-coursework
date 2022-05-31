alter table crew
    add column photo text;
alter table crew
    add column description text default 'Краткое описание для привлечения внимания клиентов';

alter table ship
    add column photo text;
alter table ship
    drop column description;
alter table ship
    add column description text default 'Краткое описание для привлечения внимания клиентов';