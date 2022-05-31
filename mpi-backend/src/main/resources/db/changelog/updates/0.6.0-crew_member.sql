alter table crew_member
    drop column member_id;
alter table crew_member
    add column full_name text;
alter table crew_member
    add column experience integer;