<%@ include file="/common/taglibs.jsp"%>

<table width="100%" class="detail" cellspacing="8">
    <tr>
        <th align="right">GiftCertificateNo</th>
        <td>${giftCertificate.giftCertificateNo}</td>
    </tr>
    <tr>
        <th align="right">Purchaser</th>
        <td>${giftCertificate.purchaser}</td>
    </tr>
    <tr>
        <th align="right">Recipient</th>
        <td>${giftCertificate.recipient}</td>
    </tr>
	<tr>
        <th align="right">message</th>
        <td>${giftCertificate.message}</td>
    </tr>
	<tr>
        <th align="right">Amount</th>
        <td>$${giftCertificate.giftCertAmt}</td>
    </tr>
	<tr>
        <th align="right">ExpireTime</th>
        <td>${giftCertificate.expireTime}</td>
    </tr>
</table>
