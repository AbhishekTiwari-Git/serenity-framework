select
ver.agr_id, ver.bil_ctm, cvi.auth_val as "ucc_id", apt.srv_typ, eml.adr_eml, CUN_TYP

from ambver_m ver
inner join ambsts_n sts on ver.agr_id = sts.agr_id and ver.agr_trm = sts.agr_trm and ver.agr_ver = sts.agr_ver
inner join ambapt_m apt on ver.agr_id = apt.agr_id and ver.agr_trm = apt.agr_trm and ver.agr_ver = apt.agr_ver
inner join CDSADR_M adr on ver.BIL_CTM = adr.CTM_NBR and adr.ADR_CDE = ver.BIL_ADR and adr.ADR_FLG = '0'
inner join CDSEML_M eml on adr.EMAL_ID = eml.EMAL_ID
inner join ambpar_m par on ver.agr_id = par.agr_id and ver.agr_trm = par.agr_trm and sts.agr_ver = par.agr_ver
inner join cdscvi_m cvi on par.ctm_nbr = cvi.ctm_nbr and cvi.auth_val like '%-%'
left join (select fut.agr_id, fut.agr_trm, fut.agr_ren, fut.srv_prd, fsts.agr_sts, fsts.abil_sts
	from ambver_m fut 
	inner join ambsts_n fsts on fut.agr_id = fsts.agr_id and fut.agr_trm = fsts.agr_trm and fut.agr_ver = fsts.agr_ver 
	inner join ambapt_m fapt on fut.agr_id = fapt.agr_id and fut.agr_trm = fapt.agr_trm and fut.agr_ver = fapt.agr_ver
	where fapt.srv_typ = 'catj-ind' and fsts.agr_sts = 'w') fut on ver.agr_id = fut.agr_id and ver.agr_trm + 1 = fut.agr_trm
where ver.srv_prd like 'y%' and ver.agr_ren = 'y' and sts.agr_sts = 'a' and sts.abil_sts = 'p' and sts.aexp_dte <> sts.afin_exp and fut.agr_ren = 'y' and fut.abil_sts <> 'p' and apt.srv_typ = 'catj-ind'and adr.cun_typ = '' and adr.ctm_ste = 'VT';



