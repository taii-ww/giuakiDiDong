package vn.udn.ute.lamquen_2415053122241 // <-- Giữ nguyên package cũ của bạn

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Biến lưu model lượt cuối và bộ đếm
    private var luotCuoi_2241: LuotNhan? = null
    private var soThuTu = 0

    // Biến lưu trạng thái phiên bổ sung
    private var phienHienTai_2241 = PhienNhan()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Lưu ý: Nếu project bạn dùng giao diện mặc định có enableEdgeToEdge thì giữ lại, không thì bỏ qua
        setContentView(R.layout.activity_main)

        // Ánh xạ View
        val edtTen = findViewById<EditText>(R.id.edtTen)
        val edtSoLuong = findViewById<EditText>(R.id.edtSoLuong)
        val btnGhiNhan = findViewById<Button>(R.id.btnGhiNhan) // Đã đổi ID từ btnKiemTra
        val btnXoa = findViewById<Button>(R.id.btnXoa)
        val txtKetQua = findViewById<TextView>(R.id.txtKetQua)
        val txtMaLuot = findViewById<TextView>(R.id.txtMaLuot)
        val txtTong = findViewById<TextView>(R.id.txtTong)
        val txtConLai = findViewById<TextView>(R.id.txtConLai)

        // Bắt sự kiện nút Ghi nhận
        btnGhiNhan.setOnClickListener {
            ghiNhan_2241(
                edtTen.text.toString(),
                edtSoLuong.text.toString(),
                txtKetQua, txtMaLuot, txtTong, txtConLai
            )
        }

        // Bắt sự kiện nút Xóa
        btnXoa.setOnClickListener {
            xoa_2241(edtTen, edtSoLuong, txtKetQua, txtMaLuot, txtTong, txtConLai)
        }
    }

    // Hàm Ghi nhận
    fun ghiNhan_2241(
        tenInput: String,
        soLuongInput: String,
        txtKetQua: TextView,
        txtMaLuot: TextView,
        txtTong: TextView,
        txtConLai: TextView
    ) {
        // 1. Chuẩn hóa dữ liệu đầu vào
        val tenChuanHoa = tenInput.chuanHoaTen_2241()
        val soLuongInt = soLuongInput.trim().toIntOrNull()

        // 2. Kiểm tra dữ liệu cơ bản (Tên 2-40, số lượng 1-9)
        if (tenChuanHoa.length !in 2..40 || soLuongInt == null || soLuongInt !in 1..9) {
            // Lỗi: Xóa kết quả cũ, ko tăng mã, gán model = null
            luotCuoi_2241 = null
            txtKetQua.text = ""
            txtMaLuot.text = ""
            Toast.makeText(this, "Lỗi: Tên 2-40 ký tự, số lượng 1-9!", Toast.LENGTH_SHORT).show()
            return
        }

// 3. Kiểm tra sức chứa (Tổng hiện tại + lượng mới <= 9)

        if (phienHienTai_2241.daNhan_2241 + soLuongInt > 9) {
            // Lỗi: Vượt sức chứa
            luotCuoi_2241 = null
            txtKetQua.text = ""
            txtMaLuot.text = ""
            Toast.makeText(this, "Lỗi: Vượt quá sức chứa (tối đa 9)!", Toast.LENGTH_SHORT).show()
            return
        }

        // 4. Hợp lệ: Cập nhật tổng, tăng mã, lưu model và hiển thị
        phienHienTai_2241.daNhan_2241 += soLuongInt
        soThuTu++

        val maMoi = QuyTacPhieu_2241.taoMaPhieu_2241(soThuTu)
        luotCuoi_2241 = LuotNhan(maMoi, tenChuanHoa, soLuongInt)

        // Đọc từ model đã lưu để hiển thị
        luotCuoi_2241?.let { luot ->
            txtKetQua.text = "Xin chào ${luot.hoTen_2241}, số lượng: ${luot.soLuong_2241}"
            txtMaLuot.text = luot.maLuot_2241
        }

        // Cập nhật giao diện tổng và chỗ còn lại
        txtTong.text = "Tổng đã nhận: ${phienHienTai_2241.daNhan_2241}"
        txtConLai.text = "Chỗ còn lại: ${9 - phienHienTai_2241.daNhan_2241}"
    }

    // Hàm Xóa
    fun xoa_2241(
        edtTen: EditText,
        edtSoLuong: EditText,
        txtKetQua: TextView,
        txtMaLuot: TextView,
        txtTong: TextView,
        txtConLai: TextView
    ) {
        // Xóa input và kết quả
        edtTen.text.clear()
        edtSoLuong.text.clear()
        txtKetQua.text = ""
        txtMaLuot.text = ""
        luotCuoi_2241 = null

        // Reset trạng thái phiên (tổng = 0, chỗ còn lại = 9)
        phienHienTai_2241.daNhan_2241 = 0
        txtTong.text = "Tổng đã nhận: 0"
        txtConLai.text = "Chỗ còn lại: 9"

        // Đề không yêu cầu reset soThuTu nên giữ nguyên
    }
}

// Extension function bắt buộc để ngoài class
fun String.chuanHoaTen_2241(): String {
    return this.trim()
}

