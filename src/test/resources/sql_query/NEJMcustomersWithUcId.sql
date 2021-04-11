select top 100
    sub.org_ord as "ord_nbr", cce.bil_cur,
    sub.pub_cde, sub.ctm_nbr, cvi.auth_val as "ucid", sub.sub_ref, sub.trm_nbr,
    sub.crc_sts as "sub.crc_sts",
    sub.bil_sts as "sub.bil_sts",
    cce.chg_card as "purc_chg_card", cce.card_nbr as "purc_card_nbr", cce.card_mm as "purc_card_mm", cce.card_cc + cce.card_yy as "purc_card_year",
    epc.card_mm as "card_mm", epc.card_cc + epc.card_yy as "card_year"
from cirsub_m sub
         inner join arpcct_m cct on sub.ord_nbr = cct.ord_num
         inner join arpcce_m cce on cct.cct_num = cce.cct_num
         inner join arpepi_m epi on cce.epi_idn = epi.epi_idn
         inner join arpepc_m epc on epi.epi_idn = epc.epi_idn
         left join cdscvi_m cvi on sub.ctm_nbr = cvi.ctm_nbr and cvi.auth_val like '%-%'
where
    sub.pub_cde = 'nej' --nej subs
    and sub.crc_sts = 'r' --active subs
    and cvi.auth_val is not NULL
order by sub.exp_dte DESC