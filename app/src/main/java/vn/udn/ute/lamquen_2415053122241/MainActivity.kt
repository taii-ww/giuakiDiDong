import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var luotCuoi_2241: LuotNhan? = null
    private var soThuTu = 0 // Dùng để tăng mã L1, L2...

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edtTen = findViewById<EditText>(R.id.edtTen)
        val edtSoLuong = findViewById<EditText>(R.id.edtSoLuong)
        val btnKiemTra = findViewById<Button>(R.id.btnKiemTra)
        val btnXoa = findViewById<Button>(R.id.btnXoa)
        val txtKetQua = findViewById<TextView>(R.id.txtKetQua)
        val txtMaLuot = findViewById<TextView>(R.id.txtMaLuot)

        btnKiemTra.setOnClickListener {
            kiemTra_2241(edtTen.text.toString(), edtSoLuong.text.toString(), txtKetQua, txtMaLuot)
        }

        btnXoa.setOnClickListener {
            xoa_2241(edtTen, edtSoLuong, txtKetQua, txtMaLuot)
        }
    }


    fun kiemTra_2241(tenInput: String, soLuongInput: String, txtKetQua: TextView, txtMaLuot: TextView) {
        // 1. Chuẩn hóa tên
        val tenChuanHoa = tenInput.chuanHoaTen_2241()

        // 2. Kiểm tra số lượng
        val soLuongInt = soLuongInput.trim().toIntOrNull()

        // 3. Validate (Tên 2-40 ký tự, số lượng 1-9)
        if (tenChuanHoa.length !in 2..40 || soLuongInt == null || soLuongInt !in 1..9) {
            // Lỗi: Xóa kết quả cũ, không tăng mã, gán model = null
            luotCuoi_2241 = null
            txtKetQua.text = ""
            txtMaLuot.text = ""
            Toast.makeText(this, "Dữ liệu không hợp lệ!", Toast.LENGTH_SHORT).show()
            return
        }

// 4. Hợp lệ: Tăng mã, lưu Model
        soThuTu++
        val maMoi = QuyTacPhieu_2241.taoMaPhieu_2241(soThuTu)
        luotCuoi_2241 = LuotNhan(maMoi, tenChuanHoa, soLuongInt)

        // 5. Hiển thị (Đọc từ model đã lưu)
        luotCuoi_2241?.let { luot ->
            txtKetQua.text = "Xin chào ${luot.hoTen_2241}, số lượng: ${luot.soLuong_2241}"
            txtMaLuot.text = luot.maLuot_2241
        }
    }
    fun xoa_2241(edtTen: EditText, edtSoLuong: EditText, txtKetQua: TextView, txtMaLuot: TextView) {
        edtTen.text.clear()
        edtSoLuong.text.clear()
        txtKetQua.text = ""
        txtMaLuot.text = ""
        luotCuoi_2241 = null
        // Đề không bắt reset bộ đếm soThuTu, nên giữ nguyên
    }
}

