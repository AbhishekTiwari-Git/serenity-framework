select ver.bil_ctm, ver.ord_ctm, ver.agr_id, sts.agr_sts, cv.auth_val
from ambver_m ver
inner join ambsts_n sts on ver.agr_id = sts.agr_id and ver.agr_trm = sts.agr_trm and ver.agr_ver = sts.agr_ver and sts.agr_sts <> 'x'
inner join ambapt_m apt on ver.agr_id = apt.agr_id and ver.agr_trm = apt.agr_trm and ver.agr_ver = apt.agr_ver
left join cdscvi_m cv on ver.bil_ctm = cv.ctm_nbr and cv.auth_val like '%-%'
where
apt.srv_typ = 'catj-inst' --catalyst institution
and sts.agr_sts = 'a' --active
--and cv.auth_val is null --no ucid