# Kiến thức - Trie

Trie là cây lưu chuỗi theo từng ký tự. Các từ có chung prefix sẽ dùng chung đường đi trên cây.

## Khi dùng

- Tìm kiếm theo prefix.
- Autocomplete.
- Dictionary lookup.
- Word search trên grid.
- Tối ưu nhiều truy vấn string.

## Complexity

Với `L` là độ dài từ:

| Operation | Complexity |
|---|---:|
| Insert | `O(L)` |
| Search | `O(L)` |
| StartsWith | `O(L)` |

## Lưu ý

- Với chữ thường `a-z`, có thể dùng array 26 phần tử.
- Với charset rộng, dùng `Map<Character, TrieNode>`.
- Cần đánh dấu `isWord` để phân biệt prefix và từ hoàn chỉnh.

