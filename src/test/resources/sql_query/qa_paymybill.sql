select top 20
pdh.dbt_nbr, pdh.tot_dbt, pdh.tot_pad, pdh.src_ctm, cvi.auth_val as "ucc id", pdh.agr_id, apt.srv_typ, eml.adr_eml
from arppdh_m pdh
inner join ambver_m ver on pdh.dbt_nbr = ver.ord_num
inner join ambsts_n sts on ver.agr_id = sts.agr_id and ver.agr_trm = sts.agr_trm and ver.agr_ver = sts.agr_ver
inner join ambapt_m apt on ver.agr_id = apt.agr_id and ver.agr_trm = apt.agr_trm and ver.agr_ver = apt.agr_ver
inner join CDSADR_M adr on ver.BIL_CTM = adr.CTM_NBR and adr.ADR_CDE = ver.BIL_ADR and adr.ADR_FLG = '0'
inner join CDSEML_M eml on adr.EMAL_ID = eml.EMAL_ID
inner join ambpar_m par on ver.agr_id = par.agr_id and ver.agr_trm = par.agr_trm and sts.agr_ver = par.agr_ver
inner join cdscvi_m cvi on par.ctm_nbr = cvi.ctm_nbr and cvi.auth_val like '%-%'
where
apt.srv_typ = 'catj-ind'
and ADR_EML like '%nejmautoemail.com'
and sts.abil_sts = 'b' --billed orders
and sts.agr_sts in ('a') --active and bill suspends
and pdh.dbt_pad = 'n'