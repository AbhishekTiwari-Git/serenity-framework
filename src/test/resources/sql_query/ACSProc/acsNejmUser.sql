select top 100
sub.ctm_nbr,
sub.org_ord,
sub.po_nbr,
sub.pub_cde, sub.bil_sts, sub.crc_sts, eml.adr_emal, sub.strt_dte, sub.EXP_DTE, adr.atn_1st, adr.atn_end, sub.ctm_nbr, sub.bil_cur, sub.rate+dtl.tax as "amt_due"
from cirsub_m sub
inner join cdsadr_m adr on sub.ctm_nbr = adr.ctm_nbr and sub.adr_cde = adr.adr_cde and adr.adr_flg = '0'
inner join cdseml_m eml on adr.emal_id = eml.emal_id
inner join cirdtl_m dtl on sub.org_ord = dtl.ord_nbr
inner join arppdh_m pdh on dtl.ord_nbr = pdh.dbt_nbr
where
sub.pub_cde = 'nej'
and sub.crc_sts in ('r')
order by sub.exp_dte DESC