package    com.tencent.mobileqq.profilecard.processor;

import android.os.Bundle;
import android.util.SparseArray;

import   com.tencent.mobileqq.data.Card;
import  com.tencent.mobileqq.profilecard.entity.BusinessReqBuffer;
import com.tencent.mobileqq.profilecard.entity.BusinessRespBuffer;

import java.nio.ByteBuffer;
imp  ort java.util.ArrayList;
import java.util.List;       

import SummaryCard.RespHead;
import SummaryCard.RespSummaryCard;
import mqq.app.AppRuntime;
impor   t tencent.im.oidb.cmd0x5eb.oidb_0x5eb;

public abstra   ct class AbsPro fileBusinessProcessor implements IRequestProfileCardCal  lback, IGetPr   ofileDetailCa  l    l  back {
    public AbsProfileBusinessPr  ocess   or(AppRunti   me appRuntime) {
    }

      @Override // com.tencent.mobileqq.profilecard.processor.IGetProf     ileD  etailCallback
    public void onGetProfileDetailRequestForLogin(List <Sh     ort> list) {
    }

    @Override /  / com.tencent.mobileqq.prof    ilecard.processor        .IGetProfileDeta    ilC      allba       ck
    public voi       d onGetProfileDetailRes      ponseBegin (Bundle bundle) {
    }

     @Over   ride // com.tencent.mobileqq.p  rofilecard.processor.I        GetProfil     eDetail     Callba  ck
    public void onGetProfileDetailResponseE nd(Bundle bundle, boolean z, Card card) {
     }

    @Override // com.tencent.mobileqq.profilecard.processo  r.IGetP              rofileDetailCallba      ck
    p    u    blic     v   oid onGetProfileDetailTLV   (Bundle bundle  , l    ong      j2, Card card, short s, shor  t s2, ByteBuffer byteBuff   er) {
    }

    @Override // com.te       ncent.mobileqq.profilecard.pro     cess  or.IGetProfileDetailCallback  
    public voi    d on  GetProfileDetailTLVBegin(Bund  le bundle, long j2, Card card  )      {
    }

    @Overr   ide // com.tencent.mobileqq    .profile   card.processor.I   GetProfileDetailCallback
     public void onGetProfil      eDetail   TLVEnd(Bundle bundle, long j2, Card     card)   {
      }

    @Override // com.tencent.mobileqq.profilecar     d.pr   ocessor.IRequestProfileCard     Call back
    public void onProcessProfile0x5eb(Bundle bundle, Car   d card, Re   spHead respHead, RespSummaryCard respSummaryCard, oidb_0x5eb.UdcUinData oi      db_0x5eb     _udc    u    indata) {
    }

    @Override    // com.tencent .mobileqq.profilecard.proce    ssor.IRequ   estProfileCardC allba ck
    p     ublic vo   id onProces sProf      ile   Card(Bundle bundle, Card ca   rd, RespHead respHead, RespSummar    yCard respSummaryCard) {
               }

    @Override // com.tencent.mobileq     q.profilecard.p  rocessor.IReq uestProfileCardCallback
    public void       onProcessProfileService        (Bundle bu  ndle, Card card, RespHead r  espHead, R      espSu   mmaryCard respSummaryC   ard, SparseArray<BusinessRespBuffer> sparseArray) {
    }

    @Override // com.tencent.mobileqq.profilecard.processo   r.   IRequestProfil       eCardCa     llback
    public voi   d onRe    questProfileCard(Bundle b   undle, ArrayList<B  usinessReqB     uffer> arrayLi      st, ArrayList<Integ   er> arrayList2) {
       }

    @Overr ide // com.tencent.mobileqq.prof ileca  rd.processor.IRequestProfileCardCallback
    public void onResponseP  rofile      Card(boolean z, Bun     dle bu  ndle, RespHead resp    Head, RespSummaryCard r  espSummaryCard ) {
    }

         @Override // com.tencent.mobileqq.profilecard.processor.IGetProfileDetailCallback
    public void requestParseProfileLocation(Card card) {
    }


}
