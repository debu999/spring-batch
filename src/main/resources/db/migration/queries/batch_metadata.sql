select
    e.address,
    e."age",
    e.created_at,
    e.date_of_birth,
    e.designation,
    e.first_name,
    e."id",
    e.joined_on,
    e.last_name,
    e.phone_number,
    e.updated_at
from
    employee e;

select * from batchdb.batch_job_execution;

select * from batch_step_execution;

select bji.job_instance_id, bji.version, bji.job_name, bji.job_key,
bje.job_execution_id, bje."version", bje.start_time, bje.end_time, bje.status,
bjep.parameter_name, bjep.parameter_value, bjep.identifying,
bse.step_execution_id, bse.version, bse.step_name, bse.start_time, bse.end_time, bse.status,
bse.commit_count, bse.read_count, bse.filter_count, bse.write_count, bse.read_skip_count,
bse.write_skip_count, bse.process_skip_count, bse.rollback_count,bse.exit_code,
bjec.short_context,bsec.short_context
from batch_job_instance bji
inner join batch_job_execution bje on bji.job_instance_id = bje.job_instance_id
inner join batch_job_execution_params bjep on bje.job_execution_id = bjep.job_execution_id
inner join batch_job_execution_context bjec on bje.job_execution_id = bjec.job_execution_id
inner join batch_step_execution bse on bje.job_execution_id = bse.job_execution_id
inner join batch_step_execution_context bsec on bse.step_execution_id = bsec.step_execution_id;