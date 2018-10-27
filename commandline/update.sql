alter table weight_note add column delivery_instruction_id integer null;
alter table weight_note add column processing_instruction_id integer null;
alter table weight_note add column shipping_instruction_id integer null;

update weight_note set delivery_instruction_id = inst_id where type = 'IM';
update weight_note set processing_instruction_id = inst_id where type = 'IP' or type='XP';
update weight_note set shipping_instruction_id = inst_id where type = 'EX';

select id from weight_note where delivery_instruction_id is null and processing_instruction_id is null and shipping_instruction_id is null;


