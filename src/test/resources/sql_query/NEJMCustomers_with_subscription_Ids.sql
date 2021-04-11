select top 20 ver.bil_ctm, ver.agr_id
from ambver_m ver
inner join ambsts_n sts on ver.agr_id = sts.agr_id and ver.agr_trm = sts.agr_trm and ver.agr_ver = sts.agr_ver and sts.agr_sts <> 'x'
inner join ambapt_m apt on ver.agr_id = apt.agr_id and ver.agr_trm = apt.agr_trm and ver.agr_ver = apt.agr_ver
where
apt.srv_typ = 'ona-amb' --ona site license
and sts.agr_sts = 'a'