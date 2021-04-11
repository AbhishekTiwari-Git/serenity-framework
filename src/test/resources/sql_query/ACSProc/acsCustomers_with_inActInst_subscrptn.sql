--inactive institution records
select v.bil_ctm, a.srv_typ, ad.atn_1st, ad.atn_end, e.adr_emal
from ambver_m v
inner join ambsts_n s on v.agr_id = s.agr_id and v.agr_trm = s.agr_trm and v.agr_ver = s.agr_ver and s.agr_sts <> 'x' and s.lst_trmf = 'y'
inner join ambapt_m a on v.agr_id = a.agr_id and v.agr_trm = a.agr_trm and v.agr_ver = a.agr_ver
inner join cdsadr_m ad on v.bil_ctm = ad.ctm_nbr and v.bil_adr = ad.adr_cde and ad.adr_flg = '0'
inner join cdseml_m e on ad.emal_id = e.emal_id
where
a.srv_typ in ('ona-amb','onc-amb','catj-inst','catj-corp') and s.agr_sts <> 'a' and v.pmo_cde <> 'agency'